package com.emissa.apps.gamergiveaways.network

import com.emissa.apps.gamergiveaways.model.Giveaways
import com.emissa.apps.gamergiveaways.utils.PlatformType
import com.emissa.apps.gamergiveaways.utils.SortType
import retrofit2.Response

interface GiveawaysRepository {
    suspend fun getAllGiveaways(sortedBy: SortType) : Response<List<Giveaways>>
    suspend fun getGiveawaysByPlatform(platform: PlatformType) : Response<List<Giveaways>>
}

class GiveawaysRepositoryImpl(
    private val giveawaysService: GiveawaysService
) : GiveawaysRepository {

    override suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>> =
        giveawaysService.getAllGiveaways(sortedBy.realValue)

    override suspend fun getGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>> =
        giveawaysService.getGiveawaysByPlatform(platform.realValue)

}