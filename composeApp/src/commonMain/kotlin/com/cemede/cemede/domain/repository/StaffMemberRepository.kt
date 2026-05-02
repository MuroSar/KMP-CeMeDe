package com.cemede.cemede.domain.repository

import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.CoroutineResult
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface StaffMemberRepository {
    suspend fun getAllStaffMembersFlow(): Flow<List<StaffMember>>

    suspend fun getStaffMemberDetailFlow(id: Int): Flow<StaffMember>

    suspend fun syncStaffMemberInfo(staffMember: StaffMember): CoroutineResult<Unit>

    suspend fun syncStaffMembersWorkingSchedule(): CoroutineResult<Unit>

    suspend fun syncAllStaffMembersSchedule(): CoroutineResult<Unit>
}
