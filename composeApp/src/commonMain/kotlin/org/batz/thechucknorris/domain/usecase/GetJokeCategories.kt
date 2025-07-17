package org.batz.thechucknorris.domain.usecase

import org.batz.thechucknorris.domain.repository.JokesRepository

class GetJokeCategories(private val jokesRepository: JokesRepository) {
    suspend fun run() =  jokesRepository.getCategories()
}
