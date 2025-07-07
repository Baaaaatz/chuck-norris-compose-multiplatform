package org.batz.thechucknorris

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import org.batz.thechucknorris.di.initKoin
import org.batz.thechucknorris.presentation.categories.CategoriesScreen
import org.batz.thechucknorris.presentation.routes.CategoriesRoute
import org.batz.thechucknorris.presentation.routes.ChuckNorrisJokesNavigation
import org.batz.thechucknorris.theme.TheChuckNorrisAppTheme
import org.koin.compose.KoinApplication

@Composable
fun App() {
    TheChuckNorrisAppTheme {
        val navController = rememberNavController()
        KoinApplication(
            application = { initKoin() },
            content = {
                NavHost(navController = navController, startDestination = ChuckNorrisJokesNavigation) {
                    navigation<ChuckNorrisJokesNavigation>(startDestination = CategoriesRoute) {
                        composable<CategoriesRoute> {
                            CategoriesScreen()
                        }
                    }
                }
            }
        )
    }
}
