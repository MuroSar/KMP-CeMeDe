package com.cemede.cemede.data.repository

import com.cemede.cemede.data.UrlProvider
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import com.cemede.cemede.data.mapper.CsvParser
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.repository.StaffMemberRepository
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime

class StaffMemberRepositoryImpl(
    private val cemedeDataBase: CemedeDataBase,
    private val csvDataSource: CSVDataSource,
) : StaffMemberRepository {
    override suspend fun getAllStaffMembersFlow(): Flow<List<StaffMember>> = cemedeDataBase.getAllStaffMembersFlow()

    override suspend fun getStaffMemberDetailFlow(id: Int): Flow<StaffMember> = cemedeDataBase.getStaffMemberDetailFlow(id)

    override suspend fun syncStaffMemberInfo(staffMember: StaffMember): CoroutineResult<Unit> {
        val staffMemberTabUrl = UrlProvider.staffMemberTabs[staffMember.name] ?: ""
        val staffMemberScheduleTabUrl = UrlProvider.staffMemberScheduleTabs[staffMember.name] ?: ""

        val allPartners = mutableListOf<PartnerEntity>()

        when (val staffMemberTabResult = csvDataSource.getStaffMemberData(staffMemberTabUrl)) {
            is CoroutineResult.Success -> {
                val partners = CsvParser.parsePartnersFromStaffMemberTab(staffMemberTabResult.data, staffMember.id)
                allPartners.addAll(partners)
                cemedeDataBase.upsertAllPartners(allPartners)

                when (val staffMemberScheduleTabResult = csvDataSource.getStaffMemberScheduleData(staffMemberScheduleTabUrl)) {
                    is CoroutineResult.Success -> {
                        val staffMemberFromDb =
                            withContext(Dispatchers.IO) { cemedeDataBase.getStaffMemberDetailFlow(staffMember.id).first() }

                        val staffMemberScheduleWithPartnersNames = CsvParser.staffMemberSchedule(staffMemberScheduleTabResult.data)
                        val partnersSchedule = mutableMapOf<DayOfWeek, Map<LocalTime, List<Partner>>>()

                        staffMemberScheduleWithPartnersNames.forEach { (day, timeMap) ->
                            val newTimeMap = mutableMapOf<LocalTime, List<Partner>>()
                            timeMap.forEach { (time, partnerNames) ->
                                val partners =
                                    partnerNames.mapNotNull { partnerName ->
                                        cemedeDataBase.getPartnerByName(partnerName)
                                    }
                                newTimeMap[time] = partners
                            }
                            partnersSchedule[day] = newTimeMap
                        }

                        val staffMemberWithSchedule =
                            StaffMemberEntity(
                                id = staffMemberFromDb.id,
                                name = staffMemberFromDb.name,
                                partnersSchedule = partnersSchedule,
                                staffMemberWorkingSchedule = staffMemberFromDb.staffMemberWorkingSchedule,
                            )

                        cemedeDataBase.upsertStaffMember(staffMemberWithSchedule)
                    }

                    is CoroutineResult.Error -> {
                        return staffMemberScheduleTabResult
                    }
                }
            }

            is CoroutineResult.Error -> {
                return staffMemberTabResult
            }
        }

        return CoroutineResult.Success(Unit)
    }

    override suspend fun syncStaffMembersWorkingSchedule(): CoroutineResult<Unit> {
        val url = UrlProvider.staffMembersWorkingScheduleTab

        when (val result = csvDataSource.getStaffMemberScheduleData(url)) {
            is CoroutineResult.Success -> {
                val schedulesByName = CsvParser.parseStaffMembersWorkingSchedule(result.data)
                val existingStaffMembers =
                    withContext(Dispatchers.IO) {
                        cemedeDataBase.getAllStaffMembersFlow().first()
                    }

                schedulesByName.forEach { (name, workingSchedule) ->
                    val staffMember = existingStaffMembers.find { it.name == name }
                    val staffMemberEntity =
                        if (staffMember != null) {
                            StaffMemberEntity(
                                id = staffMember.id,
                                name = name,
                                partnersSchedule = staffMember.partnersSchedule,
                                staffMemberWorkingSchedule = workingSchedule,
                            )
                        } else {
                            StaffMemberEntity(
                                name = name,
                                staffMemberWorkingSchedule = workingSchedule,
                            )
                        }
                    cemedeDataBase.upsertStaffMember(staffMemberEntity)
                }
                return CoroutineResult.Success(Unit)
            }

            is CoroutineResult.Error -> {
                return result
            }
        }
    }

    override suspend fun syncAllStaffMembersSchedule(): CoroutineResult<Unit> {
        val existingStaffMembers =
            withContext(Dispatchers.IO) {
                cemedeDataBase.getAllStaffMembersFlow().first()
            }

        UrlProvider.staffMemberScheduleTabs.forEach { (name, url) ->
            val staffMember = existingStaffMembers.find { it.name == name } ?: return@forEach

            when (val result = csvDataSource.getStaffMemberScheduleData(url)) {
                is CoroutineResult.Success -> {
                    val staffMemberScheduleWithPartnersNames = CsvParser.staffMemberSchedule(result.data)
                    val partnersSchedule = mutableMapOf<DayOfWeek, Map<LocalTime, List<Partner>>>()

                    staffMemberScheduleWithPartnersNames.forEach { (day, timeMap) ->
                        val newTimeMap = mutableMapOf<LocalTime, List<Partner>>()
                        timeMap.forEach { (time, partnerNames) ->
                            val partners =
                                partnerNames.mapNotNull { partnerName ->
                                    cemedeDataBase.getPartnerByName(partnerName)
                                }
                            newTimeMap[time] = partners
                        }
                        partnersSchedule[day] = newTimeMap
                    }

                    val staffMemberEntity =
                        StaffMemberEntity(
                            id = staffMember.id,
                            name = staffMember.name,
                            partnersSchedule = partnersSchedule,
                            staffMemberWorkingSchedule = staffMember.staffMemberWorkingSchedule,
                        )

                    cemedeDataBase.upsertStaffMember(staffMemberEntity)
                }

                is CoroutineResult.Error -> {
                    return result
                }
            }
        }
        return CoroutineResult.Success(Unit)
    }
}
