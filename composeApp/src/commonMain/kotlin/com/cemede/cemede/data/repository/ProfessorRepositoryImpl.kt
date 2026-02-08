package com.cemede.cemede.data.repository

import com.cemede.cemede.data.UrlProvider
import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.mapper.CsvParser
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.flow.Flow

class ProfessorRepositoryImpl(
    private val cemedeDataBase: CemedeDataBase,
    private val csvDataSource: CSVDataSource,
) : ProfessorRepository {
    override fun getAllProfessors(): Flow<List<Professor>> = cemedeDataBase.getAllProfessors()

    override fun getProfessorDetail(id: Int): Flow<Professor> = cemedeDataBase.getProfessorDetail(id)

    override suspend fun syncProfessors(): CoroutineResult<Unit> {
        val professors = mutableListOf<ProfessorEntity>()
        val allStudents = mutableListOf<com.cemede.cemede.data.data_base.model.StudentEntity>()

        for ((name, url) in UrlProvider.professorUrls) {
            when (val result = csvDataSource.getProfessorData(url)) {
                is CoroutineResult.Success -> {
                    val professorEntity = ProfessorEntity(name = name)
                    professors.add(professorEntity)
                    val students = CsvParser.parseStudents(result.data, professorEntity.id)
                    allStudents.addAll(students)
                }

                is CoroutineResult.Error -> {
                    return result
                }
            }
        }

        cemedeDataBase.upsertAllProfessors(professors)
        cemedeDataBase.upsertAllStudents(allStudents)

        return CoroutineResult.Success(Unit)
    }
}
