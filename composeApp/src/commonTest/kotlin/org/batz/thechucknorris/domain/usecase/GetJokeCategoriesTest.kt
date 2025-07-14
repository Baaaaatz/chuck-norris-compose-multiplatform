package org.batz.thechucknorris.domain.usecase

import io.mockative.coEvery
import io.mockative.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.domain.repository.JokesRepository
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetJokeCategoriesTest {
    private val jokesRepository = mock(JokesRepository::class)
    private val useCase = GetJokeCategories(jokesRepository)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads categories`() = runTest {
        val expectedCategories = listOf("Programming", "General", "Dark")
        coEvery { jokesRepository.getCategories() }.invokes { expectedCategories }
        val result = useCase.run()
        assertEquals(expectedCategories, result)
    }
}