package org.batz.thechucknorris.presentation.ui.categories

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import org.batz.thechucknorris.TestDispatchers
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.domain.usecase.GetJokeCategories
import org.batz.thechucknorris.presentation.categories.CategoriesViewModel
import org.batz.thechucknorris.util.DispatchersProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val fakeCategoriesResponse = listOf("", "")
private val mockedJokesRepository = mock<JokesRepository> {
    everySuspend { getCategories() } returns fakeCategoriesResponse
}
private val mockedGetJokeCategories = GetJokeCategories(mockedJokesRepository)

val categoriesModule = module {
    single<JokesRepository> { mockedJokesRepository }
    single<GetJokeCategories> { mockedGetJokeCategories }
    single<DispatchersProvider> { TestDispatchers() }
    viewModelOf(::CategoriesViewModel)
}
