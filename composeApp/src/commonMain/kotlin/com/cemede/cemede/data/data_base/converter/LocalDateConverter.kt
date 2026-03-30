package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun toLocalDate(date: LocalDate?): String? = date?.toString()
}
