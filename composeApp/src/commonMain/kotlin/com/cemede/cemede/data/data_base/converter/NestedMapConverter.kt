package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Student
import kotlinx.datetime.LocalTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NestedMapConverter {
    @TypeConverter
    fun fromStudentsScheduleString(value: String): Map<DayOfWeek, Map<LocalTime, List<Student>>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromStudentsScheduleMap(map: Map<DayOfWeek, Map<LocalTime, List<Student>>>): String = Json.encodeToString(map)

    @TypeConverter
    fun fromProfessorWorkingScheduleString(value: String): Map<DayOfWeek, List<LocalTime>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromProfessorWorkingScheduleMap(map: Map<DayOfWeek, List<LocalTime>>): String = Json.encodeToString(map)
}
