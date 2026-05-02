package com.cemede.cemede.presentation.screen.main

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.cemede.cemede.domain.use_case.GetAllStaffMembersFlowUseCase
import com.cemede.cemede.domain.util.NetworkHelper
import io.mockative.coEvery
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val getAllStaffMembersFlowUseCase = mock(of<GetAllStaffMembersFlowUseCase>())
    private val networkHelper = mock(of<NetworkHelper>())

    private lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val networkStatusFlow = MutableStateFlow(true)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { networkHelper.observeNetworkStatus() }.returns(networkStatusFlow)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should observe network status`() =
        runTest {
            coEvery { getAllStaffMembersFlowUseCase() }.returns(flowOf(emptyList()))

            viewModel = MainViewModel(getAllStaffMembersFlowUseCase, networkHelper)

            viewModel.state.test {
                networkStatusFlow.value = false
                assertThat(awaitItem().showNetworkBanner).isTrue()

                networkStatusFlow.value = true
                assertThat(awaitItem().showNetworkBanner).isFalse()
            }
        }

    @Test
    fun `dismissNetworkBanner should hide the banner`() =
        runTest {
            coEvery { getAllStaffMembersFlowUseCase() }.returns(flowOf(emptyList()))

            networkStatusFlow.value = false
            viewModel = MainViewModel(getAllStaffMembersFlowUseCase, networkHelper)

            viewModel.state.test {
                // El estado inicial (post-init) tiene el banner visible (!isAvailable)
                assertThat(awaitItem().showNetworkBanner).isTrue()

                viewModel.dismissNetworkBanner()
                assertThat(awaitItem().showNetworkBanner).isFalse()
            }
        }
}
