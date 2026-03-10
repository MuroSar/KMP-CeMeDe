package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.util.CoroutineResult

fun interface SyncProfessorInfoUseCase {
    suspend operator fun invoke(professor: Professor): CoroutineResult<Unit>
}

class SyncProfessorInfoUseCaseImpl(
    private val repository: ProfessorRepository,
) : SyncProfessorInfoUseCase {
    override suspend operator fun invoke(professor: Professor): CoroutineResult<Unit> = repository.syncProfessorInfo(professor)
}
