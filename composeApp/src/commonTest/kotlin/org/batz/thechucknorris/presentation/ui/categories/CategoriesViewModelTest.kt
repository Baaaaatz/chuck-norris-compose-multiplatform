package org.batz.thechucknorris.presentation.ui.categories

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.presentation.categories.CategoriesViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest : KoinTest {
    private lateinit var viewModel: CategoriesViewModel

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
            assertEquals(fakeCategoriesResponse, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
