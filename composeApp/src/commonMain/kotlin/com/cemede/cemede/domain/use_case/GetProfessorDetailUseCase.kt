package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import kotlinx.coroutines.flow.Flow

fun interface GetProfessorDetailFlowUseCase {
    suspend operator fun invoke(id: Int): Flow<Professor>
}

class GetProfessorDetailFlowUseCaseImpl(
    private val repository: ProfessorRepository,
) : GetProfessorDetailFlowUseCase {
    override suspend operator fun invoke(id: Int): Flow<Professor> = repository.getProfessorDetailFlow(id)
}
