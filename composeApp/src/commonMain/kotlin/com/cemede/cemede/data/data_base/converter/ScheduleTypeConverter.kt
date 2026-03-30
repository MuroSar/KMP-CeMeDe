package com.cemede.cemede.data.data_base.converter

import androidx.room.TypeConverter
import com.cemede.cemede.domain.model.ScheduleType

class ScheduleTypeConverter {
    @TypeConverter
    fun fromScheduleType(value: String?): ScheduleType? = value?.let { ScheduleType.valueOf(it) }

    @TypeConverter
    fun toScheduleType(type: ScheduleType?): String? = type?.name
}
