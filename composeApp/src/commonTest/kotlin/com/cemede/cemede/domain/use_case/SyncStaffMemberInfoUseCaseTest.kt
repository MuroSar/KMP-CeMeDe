package com.cemede.cemede.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SyncStaffMemberInfoUseCaseTest {
    private val repository = mock(of<StaffMemberRepository>())
    private lateinit var useCase: SyncStaffMemberInfoUseCase

    @BeforeTest
    fun setUp() {
        useCase = SyncStaffMemberInfoUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return success from repository`() =
        runTest {
            // Given
            val staffMember = StaffMember(id = 1, name = "Dr. House")
            coEvery { repository.syncStaffMemberInfo(staffMember) }.returns(CoroutineResult.Success(Unit))

            // When
            val result = useCase(staffMember)

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Success(Unit))
        }

    @Test
    fun `invoke should return error from repository`() =
        runTest {
            // Given
            val staffMember = StaffMember(id = 1, name = "Dr. House")
            val errorMessage = "Sync failed"
            coEvery { repository.syncStaffMemberInfo(staffMember) }.returns(CoroutineResult.Error(errorMessage))

            // When
            val result = useCase(staffMember)

            // Then
            assertThat(result).isEqualTo(CoroutineResult.Error(errorMessage))
        }
}
