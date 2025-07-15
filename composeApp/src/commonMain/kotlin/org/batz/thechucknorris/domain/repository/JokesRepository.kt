package org.batz.thechucknorris.domain.repository

interface JokesRepository {
    suspend fun getCategories(): List<String>
    suspend fun getJoke(category: String): String
}
