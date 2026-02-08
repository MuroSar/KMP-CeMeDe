package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.util.CoroutineResult

fun interface SyncProfessorsUseCase {
    suspend operator fun invoke(): CoroutineResult<Unit>
}

class SyncProfessorsUseCaseImpl(
    private val repository: ProfessorRepository,
) : SyncProfessorsUseCase {
    override suspend operator fun invoke(): CoroutineResult<Unit> = repository.syncProfessors()
}
