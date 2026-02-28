package com.cemede.cemede.domain.model

data class Professor(
    val id: Int,
    val name: String,
    val isWorking: Boolean,
    val students: List<Student> = emptyList(),
    val studentsSchedule: Map<DayOfWeek, Map<String, List<String>>> = emptyMap(),
)
