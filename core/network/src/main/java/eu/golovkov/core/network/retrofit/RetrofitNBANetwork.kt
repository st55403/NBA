package eu.golovkov.core.network.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import eu.golovkov.core.network.NBANetworkDataSource
import eu.golovkov.core.network.model.NetworkPlayer
import eu.golovkov.core.network.model.NetworkTeam
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://www.balldontlie.io/api/v1/"

private interface RetrofitNBANetworkApi {
    @GET(value = "players")
    suspend fun getNBAPlayers(): List<NetworkPlayer>

    @GET(value = "players/{id}")
    suspend fun getNBAPlayerDetails(
        @Path("id") id: Int
    ): NetworkPlayer

    @GET(value = "teams/{id}")
    suspend fun getNBATeamDetails(
        @Path("id") id: Int
    ): NetworkTeam
}

class RetrofitNBANetwork : NBANetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(RetrofitNBANetworkApi::class.java)

    override suspend fun getNBAPlayers(): List<NetworkPlayer> =
        networkApi.getNBAPlayers()

    override suspend fun getNBAPlayerDetails(id: Int): NetworkPlayer =
        networkApi.getNBAPlayerDetails(id)

    override suspend fun getNBATeamDetails(id: Int): NetworkTeam =
        networkApi.getNBATeamDetails(id)
}