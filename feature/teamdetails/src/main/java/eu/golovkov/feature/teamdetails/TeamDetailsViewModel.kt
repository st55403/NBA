package eu.golovkov.feature.teamdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.golovkov.core.model.data.Team
import eu.golovkov.core.network.RetrofitNBANetworkApi
import eu.golovkov.core.network.model.asTeam
import eu.golovkov.core.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamDetailsViewModel(
    private val apiService: RetrofitNBANetworkApi,
) : ViewModel() {
    private val mutableState: MutableStateFlow<UiState<Team>> =
        MutableStateFlow(UiState.Loading)
    val state: StateFlow<UiState<Team>> = mutableState.asStateFlow()

    fun getTeamDetails(teamId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teamDetails = apiService.getNBATeamDetails(teamId).asTeam()
                mutableState.value = UiState.Success(teamDetails)
            } catch (e: Exception) {
                mutableState.value = UiState.Error
            }
        }
    }
}
