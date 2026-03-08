package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.util.CoroutineResult

fun interface SyncProfessorScheduleUseCase {
    suspend operator fun invoke(professor: Professor): CoroutineResult<Unit>
}

class SyncProfessorScheduleUseCaseImpl(
    private val repository: ProfessorRepository,
) : SyncProfessorScheduleUseCase {
    override suspend operator fun invoke(professor: Professor): CoroutineResult<Unit> = repository.syncProfessorSchedule(professor)
}
