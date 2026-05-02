package com.cemede.cemede.domain.data_base

import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.StaffMember
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface CemedeDataBase {
    suspend fun getAllStaffMembersFlow(): Flow<List<StaffMember>>

    suspend fun getStaffMemberDetailFlow(id: Int): Flow<StaffMember>

    suspend fun getPartnerByName(name: String): Partner?

    suspend fun upsertStaffMember(staffMember: StaffMemberEntity): Long

    suspend fun upsertAllPartners(allPartners: List<PartnerEntity>)

    suspend fun getAllPartnersFlow(): Flow<List<Partner>>

    suspend fun getStaffMemberIdByName(name: String): Int?
}
