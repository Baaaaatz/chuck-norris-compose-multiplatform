package org.batz.thechucknorris.domain.usecase

import kotlinx.coroutines.withContext
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.util.DispatchersProvider

class GetJoke(
    private val jokesRepository: JokesRepository,
    private val dispatchersProvider: DispatchersProvider,
) {
    suspend fun run(category: String) = withContext(dispatchersProvider.io) { jokesRepository.getJoke(category) }
}
