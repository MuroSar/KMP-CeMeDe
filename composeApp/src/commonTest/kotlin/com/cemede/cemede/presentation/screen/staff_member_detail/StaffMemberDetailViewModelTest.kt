package com.cemede.cemede.presentation.screen.staff_member_detail

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.use_case.GetStaffMemberDetailFlowUseCase
import com.cemede.cemede.domain.use_case.SyncStaffMemberInfoUseCase
import com.cemede.cemede.domain.util.CoroutineResult
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
class StaffMemberDetailViewModelTest {
    private val getStaffMemberDetailFlowUseCase = mock(of<GetStaffMemberDetailFlowUseCase>())
    private val syncStaffMemberInfoUseCase = mock(of<SyncStaffMemberInfoUseCase>())
    private val networkHelper = mock(of<NetworkHelper>())

    private lateinit var viewModel: StaffMemberDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val initialStaffMember = StaffMember(id = 1, name = "Test Doctor")
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
    fun `init should sync info and then load detail successfully`() =
        runTest {
            // Given
            val updatedStaffMember = initialStaffMember.copy(name = "Updated Doctor")

            coEvery { syncStaffMemberInfoUseCase(initialStaffMember) }.returns(CoroutineResult.Success(Unit))
            coEvery { getStaffMemberDetailFlowUseCase(initialStaffMember.id) }.returns(flowOf(updatedStaffMember))

            // When
            viewModel =
                StaffMemberDetailViewModel(
                    initialStaffMember,
                    getStaffMemberDetailFlowUseCase,
                    syncStaffMemberInfoUseCase,
                    networkHelper,
                )

            // Then
            viewModel.state.test {
                // Skip loading states and check final state
                var lastState = awaitItem()
                while (lastState.staffMember == null && lastState.error == null) {
                    lastState = awaitItem()
                }

                assertThat(lastState.staffMember).isEqualTo(updatedStaffMember)
                assertThat(lastState.isLoading).isFalse()
                assertThat(lastState.error).isEqualTo(null)
            }
        }

    @Test
    fun `syncStaffMemberInfo failure should show error`() =
        runTest {
            // Given
            val errorMessage = "Sync failed"
            coEvery { syncStaffMemberInfoUseCase(initialStaffMember) }.returns(CoroutineResult.Error(errorMessage))

            // When
            viewModel =
                StaffMemberDetailViewModel(
                    initialStaffMember,
                    getStaffMemberDetailFlowUseCase,
                    syncStaffMemberInfoUseCase,
                    networkHelper,
                )

            // Then
            viewModel.state.test {
                var lastState = awaitItem()
                while (lastState.error == null && lastState.isLoading) {
                    lastState = awaitItem()
                }

                assertThat(lastState.error).isEqualTo(errorMessage)
                assertThat(lastState.isLoading).isFalse()
            }
        }
}
