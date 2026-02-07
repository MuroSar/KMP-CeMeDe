package com.cemede.cemede.domain.model

data class Professor(
    val id: Int,
    val name: String,
    val students: List<Student> = emptyList(),
)
