package org.batz.thechucknorris.presentation.ui.joke

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import org.batz.thechucknorris.TestDispatchers
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.domain.usecase.GetJoke
import org.batz.thechucknorris.presentation.joke.JokeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

const val fakeJokeResponse = "This is a test joke!"
private val mockedJokesRepository = mock<JokesRepository> {
    everySuspend { getJoke(any()) } returns fakeJokeResponse
}
private val mockedGetJoke = GetJoke(mockedJokesRepository, TestDispatchers())

val jokeModule = module {
    single<JokesRepository> { mockedJokesRepository }
    single<GetJoke> { mockedGetJoke }
    viewModel { (category: String) ->
        JokeViewModel(category, get())
    }
}