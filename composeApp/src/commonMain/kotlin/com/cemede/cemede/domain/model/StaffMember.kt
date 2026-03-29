package com.cemede.cemede.domain.model

import kotlinx.datetime.LocalTime

data class StaffMember(
    val id: Int,
    val name: String,
    val partners: List<Partner> = emptyList(),
    val partnersSchedule: Map<DayOfWeek, Map<LocalTime, List<Partner>>> = emptyMap(),
    val staffMemberWorkingSchedule: Map<DayOfWeek, List<LocalTime>> = emptyMap(),
)
