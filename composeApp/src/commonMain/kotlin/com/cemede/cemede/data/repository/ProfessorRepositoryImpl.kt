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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime

class ProfessorRepositoryImpl(
    private val cemedeDataBase: CemedeDataBase,
    private val csvDataSource: CSVDataSource,
) : ProfessorRepository {
    override suspend fun getAllProfessorsFlow(): Flow<List<Professor>> = cemedeDataBase.getAllProfessorsFlow()

    override suspend fun getProfessorDetailFlow(id: Int): Flow<Professor> = cemedeDataBase.getProfessorDetailFlow(id)

    override suspend fun syncProfessorInfo(professor: Professor): CoroutineResult<Unit> {
        val professorTabUrl = UrlProvider.professorTabs[professor.name] ?: ""
        val professorScheduleTabUrl = UrlProvider.professorScheduleTabs[professor.name] ?: ""

        val allStudents = mutableListOf<StudentEntity>()

        when (val professorTabResult = csvDataSource.getProfessorData(professorTabUrl)) {
            is CoroutineResult.Success -> {
                // Agrego todos los estudiantes
                val students = CsvParser.parseStudents(professorTabResult.data, professor.id)
                allStudents.addAll(students)
                cemedeDataBase.upsertAllStudents(allStudents)

                when (val professorScheduleTabResult = csvDataSource.getProfessorScheduleData(professorScheduleTabUrl)) {
                    is CoroutineResult.Success -> {
                        // Obtengo el professor completo de la DB
                        val professorFromDb = withContext(Dispatchers.IO) { cemedeDataBase.getProfessorDetailFlow(professor.id).first() }

                        // Parseo todos los horarios de cada estudiante
                        val studentsScheduleWithNames = CsvParser.parseStudentsSchedule(professorScheduleTabResult.data)
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
                                studentsSchedule = studentsSchedule,
                                professorWorkingSchedule = professorFromDb.professorWorkingSchedule,
                            )

                        cemedeDataBase.upsertProfessor(professorWithSchedule)
                    }

                    is CoroutineResult.Error -> {
                        return professorScheduleTabResult
                    }
                }
            }

            is CoroutineResult.Error -> {
                return professorTabResult
            }
        }

        return CoroutineResult.Success(Unit)
    }

    override suspend fun syncProfessorsWorkingSchedule(): CoroutineResult<Unit> {
        val url = UrlProvider.professorsWorkingScheduleTab

        when (val result = csvDataSource.getProfessorScheduleData(url)) {
            is CoroutineResult.Success -> {
                val schedulesByName = CsvParser.parseProfessorsWorkingSchedule(result.data)
                val existingProfessors =
                    withContext(Dispatchers.IO) {
                        cemedeDataBase.getAllProfessorsFlow().first()
                    }

                schedulesByName.forEach { (name, workingSchedule) ->
                    val professor = existingProfessors.find { it.name == name }
                    val professorEntity =
                        if (professor != null) {
                            ProfessorEntity(
                                id = professor.id,
                                name = name,
                                studentsSchedule = professor.studentsSchedule,
                                professorWorkingSchedule = workingSchedule,
                            )
                        } else {
                            ProfessorEntity(
                                name = name,
                                professorWorkingSchedule = workingSchedule,
                            )
                        }
                    cemedeDataBase.upsertProfessor(professorEntity)
                }
                return CoroutineResult.Success(Unit)
            }

            is CoroutineResult.Error -> {
                return result
            }
        }
    }
}
