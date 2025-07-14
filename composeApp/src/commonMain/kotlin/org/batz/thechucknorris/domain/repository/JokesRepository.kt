package org.batz.thechucknorris.domain.repository

import io.mockative.Mockable

@Mockable
interface JokesRepository {
    suspend fun getCategories(): List<String>
    suspend fun getJoke(category: String): String
}
