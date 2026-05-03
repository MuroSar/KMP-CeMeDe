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

class SyncAllStaffMembersScheduleUseCaseTest {
    private val repository = mock(of<StaffMemberRepository>())
    private lateinit var useCase: SyncAllStaffMembersScheduleUseCase

    @BeforeTest
    fun setUp() {
        useCase = SyncAllStaffMembersScheduleUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return success from repository`() =
        runTest {
            // Given
            coEvery { repository.syncAllStaffMembersSchedule() }.returns(CoroutineResult.Success(Unit))

            // When
            val result = useCase()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }

    @Test
    fun `invoke should return error from repository`() =
        runTest {
            // Given
            val errorMessage = "Sync failed"
            coEvery { repository.syncAllStaffMembersSchedule() }.returns(CoroutineResult.Error(errorMessage))

            // When
            val result = useCase()

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Error(errorMessage))
        }
}
