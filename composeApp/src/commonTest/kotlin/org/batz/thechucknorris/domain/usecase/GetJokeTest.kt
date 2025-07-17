package org.batz.thechucknorris.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.batz.thechucknorris.TestDispatchers
import org.batz.thechucknorris.domain.repository.JokesRepository
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetJokeTest {
    private val jokesRepository = mock<JokesRepository>()
    private val useCase = GetJoke(jokesRepository, TestDispatchers())

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
        everySuspend { jokesRepository.getJoke(any()) } returns expectedJoke
        val result = useCase.run("")
        assertEquals(expectedJoke, result)
    }
}
