package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.util.CoroutineResult

fun interface SyncProfessorsWorkingScheduleUseCase {
    suspend operator fun invoke(): CoroutineResult<Unit>
}

class SyncProfessorsWorkingScheduleUseCaseImpl(
    private val professorRepository: ProfessorRepository,
) : SyncProfessorsWorkingScheduleUseCase {
    override suspend operator fun invoke(): CoroutineResult<Unit> = professorRepository.syncProfessorsWorkingSchedule()
}
