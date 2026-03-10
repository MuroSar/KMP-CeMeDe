package com.cemede.cemede.domain.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

object DateTimeHandler {
    @OptIn(ExperimentalTime::class)
    fun getCurrentDateTimeInfo(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    fun getSpanishMonth(month: Month): String =
        when (month) {
            Month.JANUARY -> "ENERO"
            Month.FEBRUARY -> "FEBRERO"
            Month.MARCH -> "MARZO"
            Month.APRIL -> "ABRIL"
            Month.MAY -> "MAYO"
            Month.JUNE -> "JUNIO"
            Month.JULY -> "JULIO"
            Month.AUGUST -> "AGOSTO"
            Month.SEPTEMBER -> "SEPTIEMBRE"
            Month.OCTOBER -> "OCTUBRE"
            Month.NOVEMBER -> "NOVIEMBRE"
            Month.DECEMBER -> "DICIEMBRE"
        }

    fun parseTime(timeLabel: String): LocalTime =
        try {
            val customFormat = LocalTime.Format {
                amPmHour(padding = Padding.NONE) // Maneja automáticamente 1 o 2 dígitos (7 o 12)
                char(':')
                minute()
                char(':')
                second()
                char(' ')
                amPmMarker("AM", "PM")
            }

            LocalTime.parse(timeLabel, customFormat)
        } catch (e: Exception) {
            LocalTime(0, 0, 0)
        }
}
