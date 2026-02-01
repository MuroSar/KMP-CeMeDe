package com.cemede.cemede.data.data_base

import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CemedeDataBaseImpl(
    private val cemedeDao: CemedeDao,
) : CemedeDataBase {
    override fun getAllProfessors(): Flow<List<Professor>> =
        cemedeDao.getAllProfessorsAndStudents().map { professorsAndStudents ->
            professorsAndStudents.map { professorAndStudents ->
                Professor(
                    name = professorAndStudents.professor.name,
                    students = professorAndStudents.students.map { Student(it.name) },
                )
            }
        }

    override fun getProfessorDetail(name: String): Flow<Professor> =
        cemedeDao.getProfessorAndStudents(name).map { professorAndStudents ->
            Professor(
                name = professorAndStudents.professor.name,
                students = professorAndStudents.students.map { Student(it.name) },
            )
        }

    override suspend fun upsertProfessors(professors: MutableList<ProfessorEntity>) {
        cemedeDao.upsertProfessors(professors)
    }

    override suspend fun upsertStudents(allStudents: MutableList<StudentEntity>) {
        cemedeDao.upsertStudents(allStudents)
    }
}
