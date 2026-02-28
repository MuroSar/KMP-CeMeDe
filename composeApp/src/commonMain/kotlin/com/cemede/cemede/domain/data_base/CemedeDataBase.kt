package com.cemede.cemede.domain.data_base

import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student
import kotlinx.coroutines.flow.Flow

interface CemedeDataBase {
    suspend fun getAllProfessorsFlow(): Flow<List<Professor>>

    suspend fun getProfessorDetailFlow(id: Int): Flow<Professor>

    suspend fun getProfessorDetail(id: Int): Professor

    suspend fun getStudentByName(name: String): Student?

    suspend fun upsertProfessor(professor: ProfessorEntity): Long

    suspend fun upsertAllStudents(allStudents: MutableList<StudentEntity>)
}
