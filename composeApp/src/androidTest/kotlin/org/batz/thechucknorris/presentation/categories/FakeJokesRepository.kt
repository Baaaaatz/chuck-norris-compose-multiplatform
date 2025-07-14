package org.batz.thechucknorris.presentation.categories

import org.batz.thechucknorris.domain.repository.JokesRepository

class FakeJokesRepository: JokesRepository {
    override suspend fun getCategories() = listOf("Category 1", "Category 2")

    override suspend fun getJoke(category: String) = "This is a fake joke, just laugh!"
}
