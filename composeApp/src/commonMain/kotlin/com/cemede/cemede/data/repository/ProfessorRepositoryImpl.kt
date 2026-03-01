package com.cemede.cemede.data.repository

import com.cemede.cemede.data.UrlProvider
import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.data.mapper.CsvParser
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.data_source.CSVDataSource
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student
import com.cemede.cemede.domain.repository.ProfessorRepository
import com.cemede.cemede.domain.util.CoroutineResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime

class ProfessorRepositoryImpl(
    private val cemedeDataBase: CemedeDataBase,
    private val csvDataSource: CSVDataSource,
) : ProfessorRepository {
    override suspend fun getAllProfessorsFlow(): Flow<List<Professor>> = cemedeDataBase.getAllProfessorsFlow()

    override suspend fun getProfessorDetailFlow(id: Int): Flow<Professor> = cemedeDataBase.getProfessorDetailFlow(id)

    override suspend fun syncProfessors(): CoroutineResult<Unit> {
        val allStudents = mutableListOf<StudentEntity>()

        for ((name, url) in UrlProvider.professorUrls) {
            when (val result = csvDataSource.getProfessorData(url)) {
                is CoroutineResult.Success -> {
                    // TODO: Revisar el mapeo de isWorking, tendríamos que primero consumir la lista de horarios
                    val professorEntity = ProfessorEntity(name = name, isWorking = true)
                    val newProfessorId = cemedeDataBase.upsertProfessor(professorEntity)
                    val students = CsvParser.parseStudents(result.data, newProfessorId.toInt())
                    allStudents.addAll(students)
                }

                is CoroutineResult.Error -> {
                    return result
                }
            }
        }

        cemedeDataBase.upsertAllStudents(allStudents)

        return CoroutineResult.Success(Unit)
    }

    override suspend fun syncProfessorSchedule(professor: Professor): CoroutineResult<Unit> {
        val url = UrlProvider.professorSchedule[professor.name] ?: ""

        when (val result = csvDataSource.getProfessorScheduleData(url)) {
            is CoroutineResult.Success -> {
                val professorFromDb = withContext(Dispatchers.IO) { cemedeDataBase.getProfessorDetail(professor.id) }

                val studentsScheduleWithNames = CsvParser.parseStudentsSchedule(result.data)
                val studentsSchedule = mutableMapOf<DayOfWeek, Map<LocalTime, List<Student>>>()

                studentsScheduleWithNames.forEach { (day, timeMap) ->
                    val newTimeMap = mutableMapOf<LocalTime, List<Student>>()
                    timeMap.forEach { (time, studentNames) ->
                        val students =
                            studentNames.mapNotNull { studentName ->
                                cemedeDataBase.getStudentByName(studentName)
                            }
                        newTimeMap[time] = students
                    }
                    studentsSchedule[day] = newTimeMap
                }

                val professorWithSchedule =
                    ProfessorEntity(
                        id = professorFromDb.id,
                        name = professorFromDb.name,
                        isWorking = professorFromDb.isWorking,
                        studentsSchedule = studentsSchedule,
                    )

                cemedeDataBase.upsertProfessor(professorWithSchedule)
            }

            is CoroutineResult.Error -> {
                return result
            }
        }
        return CoroutineResult.Success(Unit)
    }
}
