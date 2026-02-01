package com.cemede.cemede.domain.repository

import com.cemede.cemede.domain.model.CoroutineResult
import com.cemede.cemede.domain.model.Professor
import kotlinx.coroutines.flow.Flow

interface ProfessorRepository {
    fun getAllProfessors(): Flow<List<Professor>>

    fun getProfessorDetail(name: String): Flow<Professor>

    suspend fun syncProfessors(): CoroutineResult<Unit>
}
