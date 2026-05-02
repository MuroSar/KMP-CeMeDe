package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.Mockable

@Mockable
fun interface SyncAllStaffMembersScheduleUseCase {
    suspend operator fun invoke(): CoroutineResult<Unit>
}

class SyncAllStaffMembersScheduleUseCaseImpl(
    private val repository: StaffMemberRepository,
) : SyncAllStaffMembersScheduleUseCase {
    override suspend operator fun invoke(): CoroutineResult<Unit> = repository.syncAllStaffMembersSchedule()
}
