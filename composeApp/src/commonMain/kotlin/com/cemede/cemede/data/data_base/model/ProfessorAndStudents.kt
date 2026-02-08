package com.cemede.cemede.data.data_base.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProfessorAndStudents(
    @Embedded val professor: ProfessorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "professorId",
    )
    val students: List<StudentEntity>,
)
