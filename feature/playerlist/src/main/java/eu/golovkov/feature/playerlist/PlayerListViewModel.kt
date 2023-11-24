package eu.golovkov.feature.playerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import eu.golovkov.core.model.data.Player
import eu.golovkov.core.network.RetrofitNBANetworkApi
import kotlinx.coroutines.flow.Flow

class PlayerListViewModel(
    private val apiService: RetrofitNBANetworkApi,
) : ViewModel() {
    val allPlayers: Flow<PagingData<Player>> = Pager(
        config = PagingConfig(
            pageSize = 25,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { NBASource(apiService = apiService) }
    ).flow.cachedIn(viewModelScope)
}