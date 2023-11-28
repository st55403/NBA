package eu.golovkov.core.ui

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object Error : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
}