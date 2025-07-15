package org.batz.thechucknorris.presentation.ui

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.TestDispatchers
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.domain.usecase.GetJoke
import org.batz.thechucknorris.presentation.joke.JokeViewModel
import org.batz.thechucknorris.util.DispatchersProvider
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class JokeTest : KoinTest {
    private lateinit var viewModel: JokeViewModel
    private val fakeResponse = "This is a test joke!"
    private val mockedJokesRepository = mock<JokesRepository> {
        everySuspend { getJoke(any()) } returns fakeResponse
    }
    private val mockedGetJoke = GetJoke(mockedJokesRepository)

    private val jokeModule = module {
        single<JokesRepository> { mockedJokesRepository }
        single<GetJoke> { mockedGetJoke }
        single<DispatchersProvider> { TestDispatchers() }
        viewModel { (category: String) ->
            JokeViewModel(category, get(), get())
        }
    }

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
            assertEquals(fakeResponse, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
