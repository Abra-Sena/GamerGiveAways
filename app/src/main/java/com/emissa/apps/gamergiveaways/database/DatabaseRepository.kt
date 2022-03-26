package com.emissa.apps.gamergiveaways.database


import com.emissa.apps.gamergiveaways.model.Giveaways

interface DatabaseRepository {
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)
    suspend fun getAllGiveaways() : List<Giveaways>
    suspend fun getGiveawaysById(searchId: Int) : Giveaways
    suspend fun getGiveawaysByPlatform(platform: String) : List<Giveaways>
//    suspend fun deleteOneGiveaway(vararg giveaways: Giveaways)
    suspend fun deleteGiveaways(giveaways: List<Giveaways>)
}

class DatabaseRepositoryImpl(
    private val giveawaysDao: GiveawaysDao
) : DatabaseRepository {
    override suspend fun insertGiveaways(newGiveaways: List<Giveaways>) {
        giveawaysDao.insertGiveaways(newGiveaways)
    }

    override suspend fun getAllGiveaways(): List<Giveaways> {
        return giveawaysDao.getAllGiveaways()
    }

    override suspend fun getGiveawaysById(searchId: Int): Giveaways {
        return giveawaysDao.getGiveawaysById(searchId)
    }

    override suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways> {
        return giveawaysDao.getGiveawaysByPlatform(platform)
    }

    override suspend fun deleteGiveaways(giveaways: List<Giveaways>) {
        return giveawaysDao.deleteGiveaways(giveaways)
    }

//    override suspend fun deleteOneGiveaway(vararg giveaways: Giveaways) {
//        return giveawaysDao.deleteOneGiveaway(giveaways.first())
//        //return giveawaysDao.deleteOneGiveaway() // this also will work because of the 'vararg' param
//    }

}