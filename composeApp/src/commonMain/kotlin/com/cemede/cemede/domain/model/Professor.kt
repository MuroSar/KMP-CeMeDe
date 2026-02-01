package com.cemede.cemede.domain.model

data class Professor(
    val name: String,
    val students: List<Student> = emptyList(),
)
