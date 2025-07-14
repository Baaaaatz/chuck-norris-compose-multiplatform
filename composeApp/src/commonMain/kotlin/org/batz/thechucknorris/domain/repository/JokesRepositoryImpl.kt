package org.batz.thechucknorris.domain.repository

import org.batz.thechucknorris.data.remote.JokesRemoteSource

class JokesRepositoryImpl(
    private val jokesRemoteSource: JokesRemoteSource,
) : JokesRepository {
    override suspend fun getCategories() = jokesRemoteSource.getCategories()
    override suspend fun getJoke(category: String) = jokesRemoteSource.getJoke(category).value
}
