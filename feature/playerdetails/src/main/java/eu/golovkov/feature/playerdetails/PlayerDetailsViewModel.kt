package eu.golovkov.feature.playerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.golovkov.core.network.RetrofitNBANetworkApi
import eu.golovkov.core.network.model.asPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayerDetailsViewModel(
    private val apiService: RetrofitNBANetworkApi,
) : ViewModel() {
    private val mutableState: MutableStateFlow<PlayerDetailsUiState> =
        MutableStateFlow(PlayerDetailsUiState.Loading)
    val state: StateFlow<PlayerDetailsUiState> = mutableState.asStateFlow()

    fun getPlayerDetails(playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val playerDetails = apiService.getNBAPlayerDetails(playerId).asPlayer()
                mutableState.value = PlayerDetailsUiState.Success(playerDetails)
            } catch (e: Exception) {
                mutableState.value = PlayerDetailsUiState.Error
            }
        }
    }
}
