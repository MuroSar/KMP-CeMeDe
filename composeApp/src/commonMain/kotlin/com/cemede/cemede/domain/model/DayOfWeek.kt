package com.cemede.cemede.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class DayOfWeek(
    val displayName: String,
) {
    MONDAY("Lunes"),
    TUESDAY("Martes"),
    WEDNESDAY("Miércoles"),
    THURSDAY("Jueves"),
    FRIDAY("Viernes"),
    SATURDAY("Sábado"),
    SUNDAY("Domingo"),
    ;

    companion object {
        fun fromDisplayName(name: String): DayOfWeek? =
            entries.find { it.displayName.equals(name, ignoreCase = true) }
                ?: when (name.lowercase()) {
                    "miercoles" -> WEDNESDAY
                    "sabado" -> SATURDAY
                    else -> null
                }

        fun laborDays(): List<DayOfWeek> =
            listOf(
                MONDAY,
                TUESDAY,
                WEDNESDAY,
                THURSDAY,
                FRIDAY,
            )
    }
}
