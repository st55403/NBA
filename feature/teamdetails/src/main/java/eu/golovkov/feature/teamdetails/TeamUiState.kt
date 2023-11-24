package eu.golovkov.feature.teamdetails

import eu.golovkov.core.model.data.Team

sealed interface TeamUiState {
    data object Loading : TeamUiState
    data object Error : TeamUiState
    data class Success(val team: Team) : TeamUiState
}