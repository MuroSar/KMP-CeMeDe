package com.cemede.cemede.domain.model

import kotlinx.datetime.LocalTime


data class Professor(
    val id: Int,
    val name: String,
    val isWorking: Boolean,
    val students: List<Student> = emptyList(),
    val studentsSchedule: Map<DayOfWeek, Map<LocalTime, List<Student>>> = emptyMap(),
)
