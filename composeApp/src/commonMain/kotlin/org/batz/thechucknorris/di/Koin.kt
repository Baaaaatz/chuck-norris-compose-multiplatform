package org.batz.thechucknorris.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.batz.thechucknorris.data.remote.JokesRemoteSource
import org.batz.thechucknorris.domain.repository.JokesRepository
import org.batz.thechucknorris.domain.repository.JokesRepositoryImpl
import org.batz.thechucknorris.domain.usecase.GetJokeCategories
import org.batz.thechucknorris.presentation.categories.CategoriesViewModel
import org.batz.thechucknorris.util.*
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun initKoin() {
    initLogger(isDebug)
    startKoin {
        modules(
            ktorModule,
            dataModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }
}

val dataModule = module {
    singleOf(::JokesRemoteSource)
}

val repositoryModule = module {
    single<JokesRepository> { JokesRepositoryImpl(get()) }
}

val useCaseModule = module {
    singleOf(::GetJokeCategories)
}

val viewModelModule = module {
    viewModelOf(::CategoriesViewModel)
}

val ktorModule = module {
    single {
        HttpClient(httpClientEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            if (isDebug) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
        }
    }
}
