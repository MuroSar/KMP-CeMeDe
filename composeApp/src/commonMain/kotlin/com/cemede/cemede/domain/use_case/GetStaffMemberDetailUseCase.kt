package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.repository.StaffMemberRepository
import kotlinx.coroutines.flow.Flow

fun interface GetStaffMemberDetailFlowUseCase {
    suspend operator fun invoke(id: Int): Flow<StaffMember>
}

class GetStaffMemberDetailFlowUseCaseImpl(
    private val repository: StaffMemberRepository,
) : GetStaffMemberDetailFlowUseCase {
    override suspend operator fun invoke(id: Int): Flow<StaffMember> = repository.getStaffMemberDetailFlow(id)
}
