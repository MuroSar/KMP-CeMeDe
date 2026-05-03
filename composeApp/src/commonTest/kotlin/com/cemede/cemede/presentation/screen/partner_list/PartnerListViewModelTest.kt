package com.cemede.cemede.presentation.screen.partner_list

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.use_case.GetAllPartnersUseCase
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PartnerListViewModelTest {
    private val getAllPartnersFlowUseCase = mock(of<GetAllPartnersUseCase>())

    private lateinit var viewModel: PartnerListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load partners successfully`() =
        runTest {
            // Given
            val partners =
                listOf(
                    Partner(id = 1, name = "Juan Perez", processType = "A"),
                    Partner(id = 2, name = "Maria Garcia", processType = "B"),
                )
            coEvery { getAllPartnersFlowUseCase() }.returns(flowOf(partners))

            // When
            viewModel = PartnerListViewModel(getAllPartnersFlowUseCase)

            // Then
            viewModel.state.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()

                val successState = awaitItem()
                assertThat(successState.partners).isEqualTo(partners)
                assertThat(successState.error).isEqualTo(null)
            }
        }

    @Test
    fun `init should show error when flow fails`() =
        runTest {
            // Given
            val errorMessage = "Error de conexión"
            coEvery { getAllPartnersFlowUseCase() }.returns(flow { throw Exception(errorMessage) })

            // When
            viewModel = PartnerListViewModel(getAllPartnersFlowUseCase)

            // Then
            viewModel.state.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()

                val errorState = awaitItem()
                assertThat(errorState.error).isEqualTo(errorMessage)
            }
        }
}
