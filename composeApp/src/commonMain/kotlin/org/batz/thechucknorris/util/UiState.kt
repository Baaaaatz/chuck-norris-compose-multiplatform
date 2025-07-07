package org.batz.thechucknorris.util

sealed interface BaseUiState

sealed class UiState : BaseUiState {
    data object Init : UiState()
    data object Complete : UiState()
    data object Loading : UiState()
}
