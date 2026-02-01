package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professors")
data class ProfessorEntity(
    @PrimaryKey val name: String,
)
