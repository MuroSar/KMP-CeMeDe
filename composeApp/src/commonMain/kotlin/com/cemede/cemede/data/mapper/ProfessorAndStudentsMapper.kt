package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.ProfessorAndStudents
import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student

fun ProfessorAndStudents.mapToProfessor() =
    Professor(
        id = professor.id,
        name = professor.name,
        isWorking = professor.isWorking,
        students =
            students.map {
                it.mapToStudent()
            },
    )

fun StudentEntity.mapToStudent() =
    Student(
        id = id,
        name = name,
        processType = processType,
    )
