package com.cemede.cemede.domain.use_case

import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import kotlinx.coroutines.flow.Flow

fun interface GetProfessorDetailUseCase {
    operator fun invoke(name: String): Flow<Professor>
}

class GetProfessorDetailUseCaseImpl(
    private val repository: ProfessorRepository,
) : GetProfessorDetailUseCase {
    override operator fun invoke(name: String): Flow<Professor> = repository.getProfessorDetail(name)
}
