package eu.golovkov.feature.playerlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import eu.golovkov.core.model.data.Player
import eu.golovkov.core.network.RetrofitNBANetworkApi
import eu.golovkov.core.network.model.asPlayer

class NBASource(
    private val apiService: RetrofitNBANetworkApi,
) : PagingSource<Int, Player>() {
    override fun getRefreshKey(state: PagingState<Int, Player>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        return try {
            val page = params.key ?: 1
            val players = apiService.getNBAPlayers(
                page = page,
                perPage = PAGE_SIZE
            )

            LoadResult.Page(
                data = players.data.map {
                    it.asPlayer()
                },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == players.meta.totalCount) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 35
    }
}