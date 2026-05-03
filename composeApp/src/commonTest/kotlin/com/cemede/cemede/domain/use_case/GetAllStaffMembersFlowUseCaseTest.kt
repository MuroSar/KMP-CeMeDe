package com.cemede.cemede.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.repository.StaffMemberRepository
import io.mockative.coEvery
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetAllStaffMembersFlowUseCaseTest {
    private val repository = mock(of<StaffMemberRepository>())
    private lateinit var useCase: GetAllStaffMembersFlowUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetAllStaffMembersFlowUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return flow of staff members from repository`() =
        runTest {
            // Given
            val expectedStaff =
                listOf(
                    StaffMember(id = 1, name = "Dr. House"),
                    StaffMember(id = 2, name = "Dr. Strange"),
                )
            coEvery { repository.getAllStaffMembersFlow() }.returns(flowOf(expectedStaff))

            // When
            val result = useCase().first()

            // Then
            assertThat(result).isEqualTo(expectedStaff)
        }
}
