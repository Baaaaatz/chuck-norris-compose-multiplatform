package org.batz.thechucknorris.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.batz.thechucknorris.domain.usecase.GetJokeCategories

class CategoriesViewModel(private val getJokeCategories: GetJokeCategories) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            val categories = getJokeCategories.run()
            _categories.value =  categories
        }
    }
}
