package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.util.CoroutineResult

fun interface SyncStaffMemberInfoUseCase {
    suspend operator fun invoke(staffMember: StaffMember): CoroutineResult<Unit>
}

class SyncStaffMemberInfoUseCaseImpl(
    private val repository: StaffMemberRepository,
) : SyncStaffMemberInfoUseCase {
    override suspend operator fun invoke(staffMember: StaffMember): CoroutineResult<Unit> = repository.syncStaffMemberInfo(staffMember)
}
