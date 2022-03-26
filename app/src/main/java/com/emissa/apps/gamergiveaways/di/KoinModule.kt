package com.emissa.apps.gamergiveaways.di

import android.content.Context
import androidx.room.Room
import com.emissa.apps.gamergiveaways.database.DatabaseRepository
import com.emissa.apps.gamergiveaways.database.DatabaseRepositoryImpl
import com.emissa.apps.gamergiveaways.database.GiveawaysDao
import com.emissa.apps.gamergiveaways.database.GiveawaysDatabase
import com.emissa.apps.gamergiveaways.network.GiveawaysRepository
import com.emissa.apps.gamergiveaways.network.GiveawaysRepositoryImpl
import com.emissa.apps.gamergiveaways.network.GiveawaysService
import com.emissa.apps.gamergiveaways.viewmodel.GiveawaysViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    fun provideLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    fun provideNetworkService(okHttpClient: OkHttpClient): GiveawaysService =
        Retrofit.Builder()
            .baseUrl(GiveawaysService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GiveawaysService::class.java)

    fun provideGiveawaysRepo(networkService: GiveawaysService) : GiveawaysRepository =
        GiveawaysRepositoryImpl(networkService)

    single { provideLoggingInterceptor() }
    single { provideNetworkService(get()) }
    single { provideOkHttpClient(get()) }
    single { provideGiveawaysRepo(get()) }
}

// this module provides the database
val applicationModule = module {
    fun provideGiveawaysDatabase(context: Context): GiveawaysDatabase =
        Room.databaseBuilder(
            context,
            GiveawaysDatabase::class.java,
            "giveaways-db"
        ).build()

    fun provideGiveawaysDao(giveawaysDatabase: GiveawaysDatabase) : GiveawaysDao =
        giveawaysDatabase.getGiveawaysDao()

    fun provideDatabaseRepository(databaseDao: GiveawaysDao) : DatabaseRepository =
        DatabaseRepositoryImpl(databaseDao)

    single { provideGiveawaysDatabase(androidContext()) }
    single { provideDatabaseRepository(get()) }
    single { provideGiveawaysDao(get()) }
}

val viewModelModule = module {
    viewModel { GiveawaysViewModel(get(), get()) }
}