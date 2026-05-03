package com.cemede.cemede.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SyncStaffMembersWorkingScheduleUseCaseTest {
    private val repository = mock(of<StaffMemberRepository>())
    private lateinit var useCase: SyncStaffMembersWorkingScheduleUseCase

    @BeforeTest
    fun setUp() {
        useCase = SyncStaffMembersWorkingScheduleUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return success from repository`() =
        runTest {
            // Given
            coEvery { repository.syncStaffMembersWorkingSchedule() }.returns(CoroutineResult.Success(Unit))

            // When
            val result = useCase()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }

    @Test
    fun `invoke should return error from repository`() =
        runTest {
            // Given
            val errorMessage = "Error syncing schedule"
            coEvery { repository.syncStaffMembersWorkingSchedule() }.returns(CoroutineResult.Error(errorMessage))

            // When
            val result = useCase()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Error(errorMessage))
        }
}
