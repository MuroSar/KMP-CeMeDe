package com.cemede.cemede.di

import com.cemede.cemede.data.data_base.CemedeDB
import com.cemede.cemede.data.data_base.CemedeDataBaseImpl
import com.cemede.cemede.data.data_source.CSVDataSourceImpl
import com.cemede.cemede.data.repository.ProfessorRepositoryImpl
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.use_case.GetProfessorDetailUseCase
import com.cemede.cemede.domain.use_case.GetProfessorDetailUseCaseImpl
import com.cemede.cemede.domain.use_case.GetAllProfessorsUseCase
import com.cemede.cemede.domain.use_case.GetAllProfessorsUseCaseImpl
import com.cemede.cemede.domain.use_case.SyncProfessorsUseCase
import com.cemede.cemede.domain.use_case.SyncProfessorsUseCaseImpl
import com.cemede.cemede.presentation.screen.professor_detail.ProfessorDetailViewModel
import com.cemede.cemede.presentation.screen.professor_list.ProfessorListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule =
    module {
        factory { Dispatchers.IO }
        viewModelOf(::ProfessorListViewModel)
        viewModelOf(::ProfessorDetailViewModel)
    }

val useCaseModule =
    module {
        singleOf(::GetAllProfessorsUseCaseImpl).bind<GetAllProfessorsUseCase>()
        singleOf(::GetProfessorDetailUseCaseImpl).bind<GetProfessorDetailUseCase>()
        singleOf(::SyncProfessorsUseCaseImpl).bind<SyncProfessorsUseCase>()
    }

val repositoryModule =
    module {
        singleOf(::ProfessorRepositoryImpl).bind<ProfessorRepository>()
    }

val datasourceModule =
    module {
        singleOf(::CSVDataSourceImpl).bind<CSVDataSource>()
    }

val apiModule =
    module {
        single {
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                coerceInputValues = true // Force missing or null values to use the data class's default value
                isLenient = true // Allows more flexibility when parsing JSON
            }
        }
        single {
            HttpClient(get<HttpClientEngine>()) {
                // Default config for each request
                install(DefaultRequest) {}
                // Serialization with Kotlinx
                install(ContentNegotiation) {
                    json(
                        json = get<Json>(),
                        contentType = ContentType.Any,
                    )
                }
                // Logging
                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                println("Ktor Log: $message")
                            }
                        }
                    level = LogLevel.ALL
                }
                // Error management
                install(HttpCallValidator) {
                    handleResponseExceptionWithRequest { exception, request ->
                        println("Request to ${request.url} failed: ${exception.message}")
                    }
                }
                // Timeouts
                install(HttpTimeout) {
                    requestTimeoutMillis = 15_000
                    connectTimeoutMillis = 10_000
                    socketTimeoutMillis = 15_000
                }
                // Error management
                expectSuccess = true // Throw an exception if there were HTTP error (400+)
            }
        }
    }

val databaseModule =
    module {
        singleOf(::CemedeDataBaseImpl).bind<CemedeDataBase>()

        single { get<CemedeDB>().cemedeDao() }
    }
