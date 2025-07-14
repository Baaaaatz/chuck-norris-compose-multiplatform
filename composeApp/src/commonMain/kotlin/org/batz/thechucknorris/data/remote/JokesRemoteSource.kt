package org.batz.thechucknorris.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.batz.thechucknorris.domain.entities.JokeResponse
import org.batz.thechucknorris.util.NetworkingUtils

class JokesRemoteSource(
    private val httpClient: HttpClient,
) {
    suspend fun getCategories() = httpClient.get("${NetworkingUtils.BASE_URL}jokes/categories").body<List<String>>()
    suspend fun getJoke(category: String) =
        httpClient.get("${NetworkingUtils.BASE_URL}jokes/random?category=$category").body<JokeResponse>()
}
