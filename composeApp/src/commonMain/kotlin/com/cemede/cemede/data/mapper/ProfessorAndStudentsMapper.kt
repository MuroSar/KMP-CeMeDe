package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.ProfessorAndStudents
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student

fun ProfessorAndStudents.mapToProfessor() =
    Professor(
        id = professor.id,
        name = professor.name,
        students =
            students.map {
                Student(
                    id = it.id,
                    name = it.name,
                )
            },
    )
