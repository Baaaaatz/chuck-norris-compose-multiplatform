package org.batz.thechucknorris.domain.usecase

import io.mockative.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.domain.repository.JokesRepository
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetJokeTest {
    private val jokesRepository = mock(JokesRepository::class)
    private val useCase = GetJoke(jokesRepository)

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
        val expectedJoke = "This is a joke!"
        coEvery { jokesRepository.getJoke(any()) }.invokes { expectedJoke }
        val result = useCase.run(any())
        assertEquals(expectedJoke, result)
    }
}
