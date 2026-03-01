package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Student
import kotlinx.datetime.LocalTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NestedMapConverter {
    @TypeConverter
    fun fromString(value: String): Map<DayOfWeek, Map<LocalTime, List<Student>>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromMap(map: Map<DayOfWeek, Map<LocalTime, List<Student>>>): String = Json.encodeToString(map)
}
