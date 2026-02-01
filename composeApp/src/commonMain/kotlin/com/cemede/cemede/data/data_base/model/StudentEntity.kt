package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "students",
    foreignKeys = [
        ForeignKey(
            entity = ProfessorEntity::class,
            parentColumns = ["name"],
            childColumns = ["professorName"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val professorName: String,
)
