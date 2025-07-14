package org.batz.thechucknorris

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import org.batz.thechucknorris.di.initKoin
import org.batz.thechucknorris.presentation.categories.CategoriesScreen
import org.batz.thechucknorris.presentation.joke.JokeScreen
import org.batz.thechucknorris.presentation.routes.*
import org.batz.thechucknorris.theme.TheChuckNorrisAppTheme
import org.batz.thechucknorris.util.observers.ObserveAsEvents
import org.batz.thechucknorris.util.snackbar.SnackbarController
import org.batz.thechucknorris.util.snackbar.SnackbarType
import org.koin.compose.KoinApplication

@Composable
fun App() {
    TheChuckNorrisAppTheme {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        var snackbarType by remember { mutableStateOf(SnackbarType.INFO) }
        ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
            coroutineScope.launch {
                snackbarType = event.snackbarType
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(
                    event.message,
                    duration = SnackbarDuration.Short
                )
            }
        }
        KoinApplication(
            application = { initKoin() },
            content = {
                Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = ChuckNorrisJokesNavigation) {
                        navigation<ChuckNorrisJokesNavigation>(startDestination = CategoriesRoute) {
                            composable<CategoriesRoute> {
                                CategoriesScreen(navController = navController)
                            }
                            composable<JokeRoute> {
                                val args = it.toRoute<JokeRoute>()
                                JokeScreen(category = args.category)
                            }
                        }
                    }
                }
            }
        )
    }
}
