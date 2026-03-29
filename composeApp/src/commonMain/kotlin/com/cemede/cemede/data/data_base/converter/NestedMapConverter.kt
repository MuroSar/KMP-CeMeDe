package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import kotlinx.datetime.LocalTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NestedMapConverter {
    @TypeConverter
    fun fromPartnersScheduleString(value: String): Map<DayOfWeek, Map<LocalTime, List<Partner>>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromPartnersScheduleMap(map: Map<DayOfWeek, Map<LocalTime, List<Partner>>>): String = Json.encodeToString(map)

    @TypeConverter
    fun fromStaffMemberWorkingScheduleString(value: String): Map<DayOfWeek, List<LocalTime>> = Json.decodeFromString(value)

    @TypeConverter
    fun fromStaffMemberWorkingScheduleMap(map: Map<DayOfWeek, List<LocalTime>>): String = Json.encodeToString(map)
}
