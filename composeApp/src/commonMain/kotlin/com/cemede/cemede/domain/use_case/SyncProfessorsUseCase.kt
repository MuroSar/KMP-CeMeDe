package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.CoroutineResult
import com.cemede.cemede.domain.repository.ProfessorRepository

fun interface SyncProfessorsUseCase {
    suspend operator fun invoke(): CoroutineResult<Unit>
}

class SyncProfessorsUseCaseImpl(
    private val repository: ProfessorRepository,
) : SyncProfessorsUseCase {
    override suspend operator fun invoke(): CoroutineResult<Unit> = repository.syncProfessors()
}
