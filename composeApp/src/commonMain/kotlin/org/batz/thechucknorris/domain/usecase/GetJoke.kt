package org.batz.thechucknorris.domain.usecase

import org.batz.thechucknorris.domain.repository.JokesRepository

class GetJoke(private val jokesRepository: JokesRepository) {
    suspend fun run(category: String) = jokesRepository.getJoke(category)
}
