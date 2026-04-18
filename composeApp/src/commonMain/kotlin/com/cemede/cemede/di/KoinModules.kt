package com.cemede.cemede.di

import com.cemede.cemede.data.data_base.CemedeDB
import com.cemede.cemede.data.data_base.CemedeDataBaseImpl
import com.cemede.cemede.data.data_source.CSVDataSourceImpl
import com.cemede.cemede.data.repository.PartnerRepositoryImpl
import com.cemede.cemede.data.repository.StaffMemberRepositoryImpl
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.repository.PartnerRepository
import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.use_case.GetAllPartnersUseCase
import com.cemede.cemede.domain.use_case.GetAllPartnersUseCaseImpl
import com.cemede.cemede.domain.use_case.GetAllStaffMembersFlowUseCase
import com.cemede.cemede.domain.use_case.GetAllStaffMembersFlowUseCaseImpl
import com.cemede.cemede.domain.use_case.GetStaffMemberDetailFlowUseCase
import com.cemede.cemede.domain.use_case.GetStaffMemberDetailFlowUseCaseImpl
import com.cemede.cemede.domain.use_case.SyncPartnersInfoUseCase
import com.cemede.cemede.domain.use_case.SyncPartnersInfoUseCaseImpl
import com.cemede.cemede.domain.use_case.SyncStaffMemberInfoUseCase
import com.cemede.cemede.domain.use_case.SyncStaffMemberInfoUseCaseImpl
import com.cemede.cemede.domain.use_case.SyncAllStaffMembersScheduleUseCase
import com.cemede.cemede.domain.use_case.SyncAllStaffMembersScheduleUseCaseImpl
import com.cemede.cemede.domain.use_case.SyncStaffMembersWorkingScheduleUseCase
import com.cemede.cemede.domain.use_case.SyncStaffMembersWorkingScheduleUseCaseImpl
import com.cemede.cemede.domain.util.getNetworkHelper
import com.cemede.cemede.presentation.screen.main.MainViewModel
import com.cemede.cemede.presentation.screen.partner_list.PartnerListViewModel
import com.cemede.cemede.presentation.screen.splash.SplashViewModel
import com.cemede.cemede.presentation.screen.staff_member_detail.StaffMemberDetailViewModel
import com.cemede.cemede.presentation.screen.staff_member_list.StaffMemberListViewModel
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
        single { getNetworkHelper() }
        viewModelOf(::SplashViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::StaffMemberListViewModel)
        viewModelOf(::StaffMemberDetailViewModel)
        viewModelOf(::PartnerListViewModel)
    }

val useCaseModule =
    module {
        singleOf(::GetAllStaffMembersFlowUseCaseImpl).bind<GetAllStaffMembersFlowUseCase>()
        singleOf(::GetStaffMemberDetailFlowUseCaseImpl).bind<GetStaffMemberDetailFlowUseCase>()
        singleOf(::SyncStaffMemberInfoUseCaseImpl).bind<SyncStaffMemberInfoUseCase>()
        singleOf(::SyncStaffMembersWorkingScheduleUseCaseImpl).bind<SyncStaffMembersWorkingScheduleUseCase>()
        singleOf(::SyncPartnersInfoUseCaseImpl).bind<SyncPartnersInfoUseCase>()
        singleOf(::GetAllPartnersUseCaseImpl).bind<GetAllPartnersUseCase>()
        singleOf(::SyncAllStaffMembersScheduleUseCaseImpl).bind<SyncAllStaffMembersScheduleUseCase>()
    }

val repositoryModule =
    module {
        singleOf(::StaffMemberRepositoryImpl).bind<StaffMemberRepository>()
        singleOf(::PartnerRepositoryImpl).bind<PartnerRepository>()
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
