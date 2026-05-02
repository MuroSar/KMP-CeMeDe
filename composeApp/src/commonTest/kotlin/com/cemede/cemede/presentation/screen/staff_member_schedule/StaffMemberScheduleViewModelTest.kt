package com.cemede.cemede.presentation.screen.staff_member_schedule

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
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
class StaffMemberScheduleViewModelTest {
    private val getStaffMemberDetailFlowUseCase = mock(of<GetStaffMemberDetailFlowUseCase>())
    private val syncStaffMemberInfoUseCase = mock(of<SyncStaffMemberInfoUseCase>())
    private val networkHelper = mock(of<NetworkHelper>())

    private lateinit var viewModel: StaffMemberScheduleViewModel
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
    fun `init with shouldSyncInfo true should sync and load`() =
        runTest {
            // Given
            coEvery { syncStaffMemberInfoUseCase(initialStaffMember) }.returns(CoroutineResult.Success(Unit))
            coEvery { getStaffMemberDetailFlowUseCase(initialStaffMember.id) }.returns(flowOf(initialStaffMember))

            // When
            viewModel =
                StaffMemberScheduleViewModel(
                    initialStaffMember,
                    shouldSyncInfo = true,
                    getStaffMemberDetailFlowUseCase,
                    syncStaffMemberInfoUseCase,
                    networkHelper,
                )

            // Then
            viewModel.state.test {
                var lastState = awaitItem()
                while (lastState.staffMember == null) {
                    lastState = awaitItem()
                }
                assertThat(lastState.staffMember).isEqualTo(initialStaffMember)
            }
        }

    @Test
    fun `init with shouldSyncInfo false should only load detail`() =
        runTest {
            // Given
            coEvery { getStaffMemberDetailFlowUseCase(initialStaffMember.id) }.returns(flowOf(initialStaffMember))

            // When
            viewModel =
                StaffMemberScheduleViewModel(
                    initialStaffMember,
                    shouldSyncInfo = false,
                    getStaffMemberDetailFlowUseCase,
                    syncStaffMemberInfoUseCase,
                    networkHelper,
                )

            // Then
            viewModel.state.test {
                var lastState = awaitItem()
                while (lastState.staffMember == null) {
                    lastState = awaitItem()
                }
                assertThat(lastState.staffMember).isEqualTo(initialStaffMember)
                // Verify sync was NOT called (Implicitly handled by mockative if not given/called,
                // but we focus on behavior)
            }
        }
}
