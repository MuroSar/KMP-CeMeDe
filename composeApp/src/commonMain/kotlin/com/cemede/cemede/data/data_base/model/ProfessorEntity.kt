package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Student
import kotlinx.datetime.LocalTime

@Entity(
    tableName = "professors",
    indices = [Index(value = ["name"], unique = true)],
)
data class ProfessorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isWorking: Boolean,
    val studentsSchedule: Map<DayOfWeek, Map<LocalTime, List<Student>>> = emptyMap(),
)
