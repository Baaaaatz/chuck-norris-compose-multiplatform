package org.batz.thechucknorris.util

import io.ktor.client.engine.HttpClientEngine

expect val httpClientEngine: HttpClientEngine

object NetworkingUtils {
    const val BASE_URL = "https://api.chucknorris.io/"
}
