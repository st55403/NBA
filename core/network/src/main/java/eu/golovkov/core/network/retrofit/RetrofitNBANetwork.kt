package eu.golovkov.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import eu.golovkov.core.network.RetrofitNBANetworkApi
import eu.golovkov.core.network.model.NetworkPlayer
import eu.golovkov.core.network.model.NetworkPlayersResponse
import eu.golovkov.core.network.model.NetworkTeam
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private interface RetrofitNiaNetworkApi {
    @GET(value = "players")
    suspend fun getNBAPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): NetworkPlayersResponse

    @GET(value = "players/{id}")
    suspend fun getNBAPlayerDetails(
        @Path("id") id: Int
    ): NetworkPlayer

    @GET(value = "teams/{id}")
    suspend fun getNBATeamDetails(
        @Path("id") id: Int
    ): NetworkTeam
}

class RetrofitNBANetwork(url: String) : RetrofitNBANetworkApi {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val json = Json { ignoreUnknownKeys = true }
    private val networkApi = Retrofit.Builder()
        .baseUrl(url)
        .callFactory(client)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitNiaNetworkApi::class.java)

    override suspend fun getNBAPlayers(page: Int, perPage: Int): NetworkPlayersResponse =
        networkApi.getNBAPlayers(page, perPage)

    override suspend fun getNBAPlayerDetails(id: Int): NetworkPlayer =
        networkApi.getNBAPlayerDetails(id)

    override suspend fun getNBATeamDetails(id: Int): NetworkTeam =
        networkApi.getNBATeamDetails(id)
}