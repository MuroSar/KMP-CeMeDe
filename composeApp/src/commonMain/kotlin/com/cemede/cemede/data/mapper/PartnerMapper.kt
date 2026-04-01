package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.domain.model.Partner

fun PartnerEntity.mapToPartner() =
    Partner(
        id = id,
        name = name,
        entryDate = entryDate,
        processType = processType,
        syndrome = syndrome,
        diagnosis = diagnosis,
        staffMemberName = staffMemberName,
        scheduleType = scheduleType,
        workingSchedule = workingSchedule,
    )
