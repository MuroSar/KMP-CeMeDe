package com.cemede.cemede.domain.repository

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.Flow

interface ProfessorRepository {
    suspend fun getAllProfessorsFlow(): Flow<List<Professor>>

    suspend fun getProfessorDetailFlow(id: Int): Flow<Professor>

    suspend fun syncProfessors(): CoroutineResult<Unit>

    suspend fun syncProfessorSchedule(professor: Professor): CoroutineResult<Unit>
}
