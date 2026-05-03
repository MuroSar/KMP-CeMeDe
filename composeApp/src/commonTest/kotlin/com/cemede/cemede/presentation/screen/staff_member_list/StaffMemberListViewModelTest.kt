package com.cemede.cemede.presentation.screen.staff_member_list

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.use_case.GetAllStaffMembersFlowUseCase
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
class StaffMemberListViewModelTest {
    private val getAllStaffMembersFlowUseCase = mock(of<GetAllStaffMembersFlowUseCase>())

    private lateinit var viewModel: StaffMemberListViewModel
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
    fun `init should load staff members successfully`() =
        runTest {
            // Given
            val staffMembers =
                listOf(
                    StaffMember(id = 1, name = "Dr. House"),
                    StaffMember(id = 2, name = "Dr. Strange"),
                )
            coEvery { getAllStaffMembersFlowUseCase() }.returns(flowOf(staffMembers))

            // When
            viewModel = StaffMemberListViewModel(getAllStaffMembersFlowUseCase)

            // Then
            viewModel.state.test {
                // Initial state
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()

                // Success state
                val successState = awaitItem()
                assertThat(successState.isLoading).isFalse()
                assertThat(successState.staffMembers).isEqualTo(staffMembers)
                assertThat(successState.error).isEqualTo(null)
            }
        }

    @Test
    fun `init should show error when flow fails`() =
        runTest {
            // Given
            val errorMessage = "Database Error"
            coEvery { getAllStaffMembersFlowUseCase() }.returns(flow { throw Exception(errorMessage) })

            // When
            viewModel = StaffMemberListViewModel(getAllStaffMembersFlowUseCase)

            // Then
            viewModel.state.test {
                // Initial state
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()

                // Error state
                val errorState = awaitItem()
                assertThat(errorState.isLoading).isFalse()
                assertThat(errorState.error).isEqualTo(errorMessage)
            }
        }
}
