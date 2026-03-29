package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import kotlinx.datetime.LocalTime

@Entity(
    tableName = "staff_members",
    indices = [Index(value = ["name"], unique = true)],
)
data class StaffMemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val partnersSchedule: Map<DayOfWeek, Map<LocalTime, List<Partner>>> = emptyMap(),
    val staffMemberWorkingSchedule: Map<DayOfWeek, List<LocalTime>> = emptyMap(),
)
