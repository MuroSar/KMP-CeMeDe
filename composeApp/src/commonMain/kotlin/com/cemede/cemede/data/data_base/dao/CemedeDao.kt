package com.cemede.cemede.data.data_base.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.cemede.cemede.data.data_base.model.ProfessorAndStudents
import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CemedeDao {
    @Upsert
    suspend fun upsertProfessor(professors: ProfessorEntity): Long

    @Upsert
    suspend fun upsertAllStudents(students: List<StudentEntity>)

    @Transaction
    @Query("SELECT * FROM professors")
    fun getAllProfessorsAndStudents(): Flow<List<ProfessorAndStudents>>

    @Transaction
    @Query("SELECT * FROM professors WHERE id = :id")
    fun getProfessorDetailFlow(id: Int): Flow<ProfessorAndStudents>

    @Transaction
    @Query("SELECT * FROM professors WHERE id = :id")
    fun getProfessorDetail(id: Int): ProfessorAndStudents

    @Query("SELECT * FROM students WHERE name = :name LIMIT 1")
    suspend fun getStudentByName(name: String): StudentEntity?
}
