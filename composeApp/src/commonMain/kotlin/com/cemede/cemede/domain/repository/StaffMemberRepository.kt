package com.cemede.cemede.domain.repository

import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.Flow

interface StaffMemberRepository {
    suspend fun getAllStaffMembersFlow(): Flow<List<StaffMember>>

    suspend fun getStaffMemberDetailFlow(id: Int): Flow<StaffMember>

    suspend fun syncStaffMemberInfo(staffMember: StaffMember): CoroutineResult<Unit>

    suspend fun syncStaffMembersWorkingSchedule(): CoroutineResult<Unit>
}
