package org.batz.thechucknorris.domain.state

sealed class UiState {
    data object Init : UiState()
    data object Loading : UiState()
    data object Complete : UiState()
}
