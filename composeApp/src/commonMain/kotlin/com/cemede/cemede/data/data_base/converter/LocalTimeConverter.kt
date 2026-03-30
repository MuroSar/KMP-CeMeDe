package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalTime

class LocalTimeConverter {
    @TypeConverter
    fun fromLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun toLocalTime(time: LocalTime?): String? = time?.toString()
}
