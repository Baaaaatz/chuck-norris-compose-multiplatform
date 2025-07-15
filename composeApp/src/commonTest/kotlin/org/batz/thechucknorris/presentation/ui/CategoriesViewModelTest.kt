package org.batz.thechucknorris.presentation.ui

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.TestDispatchers
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.domain.usecase.GetJokeCategories
import org.batz.thechucknorris.presentation.categories.CategoriesViewModel
import org.batz.thechucknorris.util.DispatchersProvider
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest : KoinTest {
    private lateinit var viewModel: CategoriesViewModel
    private val fakeResponse = listOf("", "")
    private val mockedJokesRepository = mock<JokesRepository> {
        everySuspend { getCategories() } returns fakeResponse
    }
    private val mockedGetJokeCategories = GetJokeCategories(mockedJokesRepository)

    private val categoriesModule = module {
        single<JokesRepository> { mockedJokesRepository }
        single<GetJokeCategories> { mockedGetJokeCategories }
        single<DispatchersProvider> { TestDispatchers() }
        viewModelOf(::CategoriesViewModel)
    }

    @BeforeTest
    fun setUp() {
        startKoin { modules(categoriesModule) }
        viewModel = get()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `When categories is initialized should complete loading and emit categories`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState.Init, awaitItem())
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Complete, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When categories is loaded should emit categories`() = runTest {
        viewModel.uiState.test { cancelAndIgnoreRemainingEvents() }
        viewModel.categories.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeResponse, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
