package org.batz.thechucknorris.presentation.ui.joke

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.presentation.joke.JokeViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class JokeViewModelTest : KoinTest {
    private lateinit var viewModel: JokeViewModel

    @BeforeTest
    fun setUp() {
        startKoin { modules(jokeModule) }
        viewModel = get(parameters = { parametersOf("") })
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState should emit Init Loading then Complete when joke is initialized`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState.Init, awaitItem())
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Complete, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `joke should emit null initially and then emit joke after loading completes`() = runTest {
        viewModel.uiState.test { cancelAndIgnoreRemainingEvents() }
        viewModel.joke.test {
            assertEquals(null, awaitItem())
            assertEquals(fakeJokeResponse, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
