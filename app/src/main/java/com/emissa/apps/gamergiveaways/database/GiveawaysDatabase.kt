package com.emissa.apps.gamergiveaways.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.emissa.apps.gamergiveaways.model.Giveaways


@Database(
    entities = [Giveaways::class],
    version = 1
)
abstract class GiveawaysDatabase : RoomDatabase() {
    abstract fun getGiveawaysDao() : GiveawaysDao
}

@Dao
interface GiveawaysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)

    @Query("SELECT * FROM giveaways")
    suspend fun getAllGiveaways() : List<Giveaways>

    @Query("SELECT * FROM giveaways WHERE id = :searchId") // add LIMIT 1 to return 1 value only
    suspend fun getGiveawaysById(searchId: Int) : Giveaways

    @Query("SELECT * FROM giveaways WHERE platforms = :platform")
    suspend fun getGiveawaysByPlatform(platform: String) : List<Giveaways>

    @Delete
    suspend fun deleteGiveaways(giveaways: List<Giveaways>)
    @Delete
    suspend fun deleteOneGiveaway(vararg giveaways: Giveaways) // vararg to pass in one or more time
}