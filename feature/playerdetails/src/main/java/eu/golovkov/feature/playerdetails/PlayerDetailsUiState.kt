package eu.golovkov.feature.playerdetails

import eu.golovkov.core.model.data.Player

sealed interface PlayerDetailsUiState {
    data object Loading : PlayerDetailsUiState
    data object Error : PlayerDetailsUiState
    data class Success(val player: Player) : PlayerDetailsUiState
}