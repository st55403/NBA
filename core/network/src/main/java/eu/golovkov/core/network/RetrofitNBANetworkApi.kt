package eu.golovkov.core.network

import eu.golovkov.core.network.model.NetworkPlayer
import eu.golovkov.core.network.model.NetworkPlayersResponse
import eu.golovkov.core.network.model.NetworkTeam

interface RetrofitNBANetworkApi {
    suspend fun getNBAPlayers(page: Int, perPage: Int): NetworkPlayersResponse
    suspend fun getNBAPlayerDetails(id: Int): NetworkPlayer
    suspend fun getNBATeamDetails(id: Int): NetworkTeam
}