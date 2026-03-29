package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.repository.StaffMemberRepository
import kotlinx.coroutines.flow.Flow

fun interface GetAllStaffMembersFlowUseCase {
    suspend operator fun invoke(): Flow<List<StaffMember>>
}

class GetAllStaffMembersFlowUseCaseImpl(
    private val repository: StaffMemberRepository,
) : GetAllStaffMembersFlowUseCase {
    override suspend operator fun invoke(): Flow<List<StaffMember>> = repository.getAllStaffMembersFlow()
}
