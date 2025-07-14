package org.batz.thechucknorris.presentation.categories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.domain.state.UiState
import org.batz.thechucknorris.domain.usecase.GetJokeCategories
import org.batz.thechucknorris.util.DefaultDispatcher
import org.batz.thechucknorris.util.DispatchersProvider
import org.kodein.mock.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
@UsesMocks(GetJokeCategories::class)
class CategoriesViewModelTest : KoinTest {

    private lateinit var viewModel: CategoriesViewModel
    private val mocker = Mocker()

    @BeforeTest
    fun setUp() {
        val categoriesViewModelModule = module {
            single<DispatchersProvider> { DefaultDispatcher() }
            single<JokesRepository> { FakeJokesRepository() }
            single<GetJokeCategories> { mocker.mock<GetJokeCategories>() }
            viewModel { CategoriesViewModel(get(), get()) }
        }
        startKoin {
            modules(categoriesViewModelModule)
        }
        viewModel = get()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `loads categories and updates state`() = runTest {
        val fakeCategories = listOf("Tech", "General")
        mocker.mock<GetJokeCategories>()
        mocker.every { }
        advanceUntilIdle()
        assertEquals(fakeCategories, viewModel.categories.value)
        assertEquals(UiState.Complete, viewModel.uiState.value)
    }
}