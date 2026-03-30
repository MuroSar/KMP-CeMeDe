package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class DateTimeConverter {
    @TypeConverter
    fun fromLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun toLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun fromLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun toLocalTime(time: LocalTime?): String? = time?.toString()
}
