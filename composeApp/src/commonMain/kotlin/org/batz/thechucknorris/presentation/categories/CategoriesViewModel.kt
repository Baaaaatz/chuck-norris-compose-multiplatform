package org.batz.thechucknorris.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.domain.usecase.GetJokeCategories
import org.batz.thechucknorris.util.DispatchersProvider
import org.batz.thechucknorris.util.snackbar.*

class CategoriesViewModel(
    private val getJokeCategories: GetJokeCategories,
    private val dispatcher: DispatchersProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Init)
    val uiState = _uiState.asStateFlow()
        .onStart { onInit() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiState.Init
        )

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            SnackbarController.sendEvent(SnackbarEvent(throwable.message ?: "Something went wrong", SnackbarType.ERROR))
        }
    }

    private fun onInit() {
        viewModelScope.launch(dispatcher.io + exceptionHandler) {
            _uiState.value = UiState.Loading
            val categories = getJokeCategories.run()
            _categories.value = categories
            _uiState.value = UiState.Complete
        }
    }
}
