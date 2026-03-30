package com.cemede.cemede.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class ScheduleType(
    val displayName: String,
) {
    FIXED("Fijo"),
    ROTATIVE("Rotativo"),
    ;

    companion object {
        fun fromDisplayName(name: String): ScheduleType? = entries.find { it.displayName.equals(name, ignoreCase = true) }
    }
}
