package eu.golovkov.core.network

import eu.golovkov.core.network.model.NetworkPlayer
import eu.golovkov.core.network.model.NetworkTeam

interface NBANetworkDataSource {
    suspend fun getNBAPlayers(): List<NetworkPlayer>
    suspend fun getNBAPlayerDetails(id: Int): NetworkPlayer
    suspend fun getNBATeamDetails(id: Int): NetworkTeam
}