package com.cemede.cemede.data.data_base

import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.data.mapper.mapToProfessor
import com.cemede.cemede.domain.data_base.CemedeDataBase
import com.cemede.cemede.domain.model.Professor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CemedeDataBaseImpl(
    private val cemedeDao: CemedeDao,
) : CemedeDataBase {
    override suspend fun getAllProfessorsFlow(): Flow<List<Professor>> =
        cemedeDao.getAllProfessorsAndStudents().map { professorsAndStudents ->
            println("✅ getAllProfessors from DB, $professorsAndStudents ,SUCCESS")
            professorsAndStudents.map { professorAndStudents ->
                professorAndStudents.mapToProfessor()
            }
        }

    override suspend fun getProfessorDetailFlow(id: Int): Flow<Professor> =
        cemedeDao.getProfessorDetailFlow(id).map { professorAndStudents ->
            println("✅ getProfessorDetail from DB, $professorAndStudents , SUCCESS")
            professorAndStudents.mapToProfessor()
        }

    override suspend fun getProfessorDetail(id: Int): Professor {
        val professorAndStudents = cemedeDao.getProfessorDetail(id)
        println("✅ getProfessorDetail from DB, $professorAndStudents , SUCCESS")
        return professorAndStudents.mapToProfessor()
    }

    override suspend fun upsertProfessor(professor: ProfessorEntity): Long {
        val professorId = cemedeDao.upsertProfessor(professor)
        println("✅ upsertProfessor into DB, $professorId - $professor , SUCCESS")
        return professorId
    }

    override suspend fun upsertAllStudents(allStudents: MutableList<StudentEntity>) {
        cemedeDao.upsertAllStudents(allStudents)
        println("✅ upsertAllStudents into DB, SUCCESS")
    }
}
