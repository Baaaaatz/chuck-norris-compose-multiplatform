package org.batz.thechucknorris.presentation.joke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.domain.usecase.GetJoke
import org.batz.thechucknorris.util.DispatchersProvider
import org.batz.thechucknorris.util.snackbar.*

class JokeViewModel(
    private val category: String,
    private val getJoke: GetJoke,
    dispatcher: DispatchersProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    val uiState = _uiState.asStateFlow()

    private val _joke = MutableStateFlow<String?>(null)
    val joke = _joke.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            SnackbarController.sendEvent(SnackbarEvent(throwable.message ?: "Something went wrong", SnackbarType.ERROR))
        }
    }

    init {
        viewModelScope.launch(dispatcher.io + exceptionHandler) {
            _uiState.value = UiState.Loading
            val joke = getJoke.run(category)
            _joke.value = joke
            _uiState.value = UiState.Complete
        }
    }
}
