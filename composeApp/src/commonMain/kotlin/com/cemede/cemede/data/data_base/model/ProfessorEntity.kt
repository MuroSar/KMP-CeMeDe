package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "professors",
    indices = [Index(value = ["name"], unique = true)]
)
data class ProfessorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)
