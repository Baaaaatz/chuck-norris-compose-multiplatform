package org.batz.thechucknorris.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class JokeResponse(
    val value: String
)
