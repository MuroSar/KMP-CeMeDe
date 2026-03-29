package com.cemede.cemede.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Partner(
    val id: Int,
    val name: String,
    val processType: String,
)
