package com.emissa.apps.gamergiveaways.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emissa.apps.gamergiveaways.database.DatabaseRepository
import com.emissa.apps.gamergiveaways.network.GiveawaysRepository
import com.emissa.apps.gamergiveaways.utils.GiveawaysState
import com.emissa.apps.gamergiveaways.utils.PlatformType
import com.emissa.apps.gamergiveaways.utils.SortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * this view model will be attach to the activity
 * if attach to fragments, it will be destroy everytime that fragment is destroyed
 */
class GiveawaysViewModel(
    private val networkRepo: GiveawaysRepository,
    private val databaseRepo: DatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        Log.d("GiveawaysViewModel", "ViewModel initialized")
    }

    private val _sortedGiveaways: MutableLiveData<GiveawaysState> = MutableLiveData(GiveawaysState.LOADING)
    val giveaways: LiveData<GiveawaysState> get() = _sortedGiveaways

    var platform: PlatformType = PlatformType.ANDROID

    fun getSortedGiveaways(sortBy: SortType = SortType.DATE) {
        // create a coroutine
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getAllGiveaways(sortBy)

                if (response.isSuccessful) {
                    response.body()?.let {
                        // insert response to database
                        databaseRepo.insertGiveaways(it)
                        val localData = databaseRepo.getAllGiveaways()
                        // post value retrieved from database
                        _sortedGiveaways.postValue(GiveawaysState.SUCCESS(localData))
                    } ?: throw Exception("Body is null")
                } else {
                    // fail to retrieve data, no response
                    throw Exception("No successful response")
                }
            } catch (error: Exception) { // catch all errors here
                // required as coroutine can not handles the errors automatically
                _sortedGiveaways.postValue(GiveawaysState.ERROR(error))
                // retrieve data inside database, we want to still use the app even if there is an error retrieving data
                loadFromDatabase()
            }
        }
    }

    fun getGiveawaysByPlatform() {
        // create a coroutine
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getGiveawaysByPlatform(platform)

                if (response.isSuccessful) {
                    response.body()?.let {
                        //add response from query to database
//                        databaseRepo.insertGiveaways(it)
//                        val localDataPlatform = databaseRepo.getGiveawaysByPlatform(platform)
                        // posting directly from the local database
                        _sortedGiveaways.postValue(GiveawaysState.SUCCESS(it))
                    } ?: throw Exception("Response successful. Body is null")
                } else {
                    // fail to retrieve data, no response
                    throw Exception("No successful response")
                }
            } catch (error: Exception) { // catch all errors here
                // required as coroutine can not handles the errors automatically
                _sortedGiveaways.postValue(GiveawaysState.ERROR(error))
            }
        }

        viewModelScope.async {

        }
    }

    private suspend fun loadFromDatabase() {
        // this is only for retrieving the full data from
        try {
            val localData = databaseRepo.getAllGiveaways()
            _sortedGiveaways.postValue(GiveawaysState.SUCCESS(localData, isLocal = true))
        } catch (error: Exception) {
            _sortedGiveaways.postValue(GiveawaysState.ERROR(error))
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GiveawaysViewModel", "ViewModel destroyed")
    }

    /**
     * note: try to minimize database access and find an efficient way to save and access data
     */
}