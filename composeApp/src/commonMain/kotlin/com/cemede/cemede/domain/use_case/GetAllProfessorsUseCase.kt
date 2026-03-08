package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import kotlinx.coroutines.flow.Flow

fun interface GetAllProfessorsFlowUseCase {
    suspend operator fun invoke(): Flow<List<Professor>>
}

class GetAllProfessorsFlowUseCaseImpl(
    private val repository: ProfessorRepository,
) : GetAllProfessorsFlowUseCase {
    override suspend operator fun invoke(): Flow<List<Professor>> = repository.getAllProfessorsFlow()
}
