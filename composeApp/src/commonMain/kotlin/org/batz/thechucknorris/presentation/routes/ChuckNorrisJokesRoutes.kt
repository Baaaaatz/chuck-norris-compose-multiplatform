package org.batz.thechucknorris.presentation.routes

import kotlinx.serialization.Serializable

@Serializable
object ChuckNorrisJokesNavigation

@Serializable
object CategoriesRoute

@Serializable
data class JokeRoute(val category: String)
