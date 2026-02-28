package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Student
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NestedMapConverter {
    @TypeConverter
    fun fromString(value: String): Map<DayOfWeek, Map<String, List<Student>>> =
        try {
            Json.decodeFromString<Map<DayOfWeek, Map<String, List<Student>>>>(value)
        } catch (e: Exception) {
            emptyMap()
        }

    @TypeConverter
    fun fromMap(map: Map<DayOfWeek, Map<String, List<Student>>>): String = Json.encodeToString(map)
}
