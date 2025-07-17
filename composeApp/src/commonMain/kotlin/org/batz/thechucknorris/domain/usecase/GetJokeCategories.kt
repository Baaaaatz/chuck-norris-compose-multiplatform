package org.batz.thechucknorris.domain.usecase

import kotlinx.coroutines.*
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.util.DispatchersProvider

class GetJokeCategories(
    private val jokesRepository: JokesRepository,
    private val dispatchersProvider: DispatchersProvider,
) {
    suspend fun run() = withContext(dispatchersProvider.io) {
        jokesRepository.getCategories()
    }
}
