package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.StaffMemberAndPartners
import com.cemede.cemede.domain.model.StaffMember

fun StaffMemberAndPartners.mapToStaffMember() =
    StaffMember(
        id = staffMember.id,
        name = staffMember.name,
        partners = partners.map { it.mapToPartner() },
        partnersSchedule = staffMember.partnersSchedule,
        staffMemberWorkingSchedule = staffMember.staffMemberWorkingSchedule,
    )
