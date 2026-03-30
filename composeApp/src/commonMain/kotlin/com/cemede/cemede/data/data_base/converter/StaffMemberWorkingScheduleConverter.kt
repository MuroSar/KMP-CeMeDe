package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json

class StaffMemberWorkingScheduleConverter {
    @TypeConverter
    fun fromStaffMemberWorkingScheduleString(value: String): Map<DayOfWeek, List<LocalTime>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromStaffMemberWorkingScheduleMap(map: Map<DayOfWeek, List<LocalTime>>): String = Json.encodeToString(map)
}
