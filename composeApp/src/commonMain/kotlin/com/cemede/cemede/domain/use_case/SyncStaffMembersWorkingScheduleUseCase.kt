package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.util.CoroutineResult

fun interface SyncStaffMembersWorkingScheduleUseCase {
    suspend operator fun invoke(): CoroutineResult<Unit>
}

class SyncStaffMembersWorkingScheduleUseCaseImpl(
    private val staffMemberRepository: StaffMemberRepository,
) : SyncStaffMembersWorkingScheduleUseCase {
    override suspend operator fun invoke(): CoroutineResult<Unit> = staffMemberRepository.syncStaffMembersWorkingSchedule()
}
