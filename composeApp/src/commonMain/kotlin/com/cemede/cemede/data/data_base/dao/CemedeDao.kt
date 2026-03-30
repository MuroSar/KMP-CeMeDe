package com.cemede.cemede.data.data_base.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.data_base.model.StaffMemberAndPartners
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CemedeDao {
    @Upsert
    suspend fun upsertStaffMember(staffMembers: StaffMemberEntity): Long

    @Upsert
    suspend fun upsertAllPartners(partners: List<PartnerEntity>)

    @Transaction
    @Query("SELECT * FROM staff_members")
    fun getAllStaffMembersAndPartners(): Flow<List<StaffMemberAndPartners>>

    @Transaction
    @Query("SELECT * FROM staff_members WHERE id = :id")
    fun getStaffMemberDetailFlow(id: Int): Flow<StaffMemberAndPartners>

    @Query("SELECT * FROM partners")
    fun getAllPartners(): Flow<List<PartnerEntity>>

    @Query("SELECT * FROM partners WHERE name = :name LIMIT 1")
    suspend fun getPartnerByName(name: String): PartnerEntity?

    @Query("SELECT id FROM staff_members WHERE name = :name LIMIT 1")
    suspend fun getStaffMemberIdByName(name: String): Int?
}
