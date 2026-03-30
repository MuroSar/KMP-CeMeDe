package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json

class WorkingScheduleConverter {
    @TypeConverter
    fun fromWorkingScheduleString(value: String): Map<DayOfWeek, LocalTime> = Json.decodeFromString(value)

    @TypeConverter
    fun fromWorkingScheduleMap(map: Map<DayOfWeek, LocalTime>): String = Json.encodeToString(map)
}
