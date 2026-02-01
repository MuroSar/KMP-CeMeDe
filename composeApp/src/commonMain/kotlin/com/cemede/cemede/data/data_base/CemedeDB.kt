package com.cemede.cemede.data.data_base

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.ProfessorEntity
import com.cemede.cemede.data.data_base.model.StudentEntity

@Database(
    entities = [
        ProfessorEntity::class,
        StudentEntity::class,
    ],
    version = 1,
)
@ConstructedBy(MyDatabaseCtor::class)
abstract class CemedeDB : RoomDatabase() {
    abstract fun cemedeDao(): CemedeDao

    companion object {
        const val DATABASE_NAME = "CeMeDe.db"
    }
}

expect object MyDatabaseCtor : RoomDatabaseConstructor<CemedeDB>
