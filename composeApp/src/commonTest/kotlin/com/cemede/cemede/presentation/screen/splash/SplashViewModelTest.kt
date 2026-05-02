package com.cemede.cemede.presentation.screen.splash

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.cemede.cemede.domain.use_case.SyncAllStaffMembersScheduleUseCase
import com.cemede.cemede.domain.use_case.SyncPartnersInfoUseCase
import com.cemede.cemede.domain.use_case.SyncStaffMembersWorkingScheduleUseCase
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {
    private val syncStaffMembersWorkingScheduleUseCase = mock(of<SyncStaffMembersWorkingScheduleUseCase>())
    private val syncPartnersInfoUseCase = mock(of<SyncPartnersInfoUseCase>())
    private val syncAllStaffMembersScheduleUseCase = mock(of<SyncAllStaffMembersScheduleUseCase>())

    private lateinit var viewModel: SplashViewModel
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
    fun `init should sync all data successfully`() =
        runTest {
            // Given
            coEvery { syncStaffMembersWorkingScheduleUseCase() }.returns(CoroutineResult.Success(Unit))
            coEvery { syncPartnersInfoUseCase() }.returns(CoroutineResult.Success(Unit))
            coEvery { syncAllStaffMembersScheduleUseCase() }.returns(CoroutineResult.Success(Unit))

            // When
            viewModel =
                SplashViewModel(
                    syncStaffMembersWorkingScheduleUseCase,
                    syncPartnersInfoUseCase,
                    syncAllStaffMembersScheduleUseCase,
                )

            // Then
            viewModel.state.test {
                // Initial state (or state after first sync launch)
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()

                // Wait for all syncs to complete
                var finalState = awaitItem()
                while (!finalState.isSyncComplete && finalState.isLoading) {
                    // Skip intermediate message updates if any
                    finalState = awaitItem()
                }

                assertThat(finalState.isSyncComplete).isTrue()
                assertThat(finalState.isLoading).isFalse()
                assertThat(finalState.error).isEqualTo(null)
            }
        }

    @Test
    fun `init should show error when first sync fails`() =
        runTest {
            // Given
            val errorMessage = "Network Error"
            coEvery { syncStaffMembersWorkingScheduleUseCase() }.returns(CoroutineResult.Error(errorMessage))

            // When
            viewModel =
                SplashViewModel(
                    syncStaffMembersWorkingScheduleUseCase,
                    syncPartnersInfoUseCase,
                    syncAllStaffMembersScheduleUseCase,
                )

            // Then
            viewModel.state.test {
                val state = awaitItem()
                // We might need to skip some items depending on how quickly the flow updates
                var lastState = state
                while (lastState.error == null && lastState.isLoading) {
                    lastState = awaitItem()
                }

                assertThat(lastState.error).isNotNull()
                assertThat(lastState.error).equals(errorMessage)
                assertThat(lastState.isLoading).isFalse()
            }
        }
}
