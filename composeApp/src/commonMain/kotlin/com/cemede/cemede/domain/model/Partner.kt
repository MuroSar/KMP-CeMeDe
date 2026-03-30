package com.cemede.cemede.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class Partner(
    val id: Int,
    val name: String,
    val entryDate: LocalDate? = null,
    val processType: String,
    val syndrome: String = "",
    val diagnosis: String = "",
    val staffMemberName: String = "",
    val scheduleType: ScheduleType? = null,
    val workingSchedule: Map<DayOfWeek, LocalTime> = emptyMap(),
)
