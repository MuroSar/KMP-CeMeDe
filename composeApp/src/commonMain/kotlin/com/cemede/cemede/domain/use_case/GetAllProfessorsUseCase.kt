package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import kotlinx.coroutines.flow.Flow

fun interface GetAllProfessorsUseCase {
    operator fun invoke(): Flow<List<Professor>>
}

class GetAllProfessorsUseCaseImpl(
    private val repository: ProfessorRepository,
) : GetAllProfessorsUseCase {
    override operator fun invoke(): Flow<List<Professor>> = repository.getAllProfessors()
}
