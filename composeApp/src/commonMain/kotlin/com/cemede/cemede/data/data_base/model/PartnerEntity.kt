package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.ScheduleType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Entity(
    tableName = "partners",
    foreignKeys = [
        ForeignKey(
            entity = StaffMemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["staffMemberId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["name"], unique = true)],
)
data class PartnerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val entryDate: LocalDate? = null,
    val processType: String,
    val syndrome: String = "",
    val diagnosis: String = "",
    val staffMemberName: String = "",
    val staffMemberId: Int? = null,
    val scheduleType: ScheduleType? = null,
    val workingSchedule: Map<DayOfWeek, LocalTime> = emptyMap(),
)
