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

class GetStaffMemberDetailFlowUseCaseTest {
    private val repository = mock(of<StaffMemberRepository>())
    private lateinit var useCase: GetStaffMemberDetailFlowUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetStaffMemberDetailFlowUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return staff member detail flow from repository`() =
        runTest {
            // Given
            val staffId = 1
            val expectedStaff = StaffMember(id = staffId, name = "Dr. House")
            coEvery { repository.getStaffMemberDetailFlow(staffId) }.returns(flowOf(expectedStaff))

            // When
            val result = useCase(staffId).first()

            // Then
            assertThat(result).isEqualTo(expectedStaff)
        }
}
