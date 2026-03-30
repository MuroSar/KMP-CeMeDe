package com.cemede.cemede.data.data_base

import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.mapper.mapToStaffMember
import com.cemede.cemede.data.mapper.mapToPartner
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.model.Partner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CemedeDataBaseImpl(
    private val cemedeDao: CemedeDao,
) : CemedeDataBase {
    override suspend fun getAllStaffMembersFlow(): Flow<List<StaffMember>> =
        cemedeDao.getAllStaffMembersAndPartners().map { staffMembersAndPartners ->
            staffMembersAndPartners.map { staffMemberAndPartners ->
                staffMemberAndPartners.mapToStaffMember()
            }
        }

    override suspend fun getStaffMemberDetailFlow(id: Int): Flow<StaffMember> =
        cemedeDao.getStaffMemberDetailFlow(id).map { staffMemberAndPartners ->
            staffMemberAndPartners.mapToStaffMember()
        }

    override suspend fun getPartnerByName(name: String): Partner? {
        val partner = cemedeDao.getPartnerByName(name)
        return partner?.mapToPartner()
    }

    override suspend fun upsertStaffMember(staffMember: StaffMemberEntity): Long {
        return cemedeDao.upsertStaffMember(staffMember)
    }

    override suspend fun upsertAllPartners(allPartners: List<PartnerEntity>) {
        cemedeDao.upsertAllPartners(allPartners)
    }

    override suspend fun getAllPartnersFlow(): Flow<List<Partner>> =
        cemedeDao.getAllPartners().map { partners ->
            partners.map { it.mapToPartner() }
        }

    override suspend fun getStaffMemberIdByName(name: String): Int? {
        return cemedeDao.getStaffMemberIdByName(name)
    }
}
