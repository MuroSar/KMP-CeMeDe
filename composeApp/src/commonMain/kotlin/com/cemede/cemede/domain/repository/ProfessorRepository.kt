package com.cemede.cemede.domain.repository

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.Flow

interface ProfessorRepository {
    fun getAllProfessors(): Flow<List<Professor>>

    fun getProfessorDetail(id: Int): Flow<Professor>

    suspend fun syncProfessors(): CoroutineResult<Unit>
}
