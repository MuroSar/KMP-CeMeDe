package com.cemede.cemede.domain.data_base

import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.domain.model.Professor
import kotlinx.coroutines.flow.Flow

interface CemedeDataBase {
    fun getAllProfessors(): Flow<List<Professor>>
    fun getProfessorDetail(name: String): Flow<Professor>
    suspend fun upsertProfessors(professors: MutableList<ProfessorEntity>)
    suspend fun upsertStudents(allStudents: MutableList<StudentEntity>)
}
