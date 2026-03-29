package com.cemede.cemede.data.data_base

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.cemede.cemede.data.data_base.converter.NestedMapConverter
import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import com.cemede.cemede.data.data_base.model.PartnerEntity

@Database(
    entities = [
        StaffMemberEntity::class,
        PartnerEntity::class,
    ],
    version = 1,
)
@ConstructedBy(MyDatabaseCtor::class)
@TypeConverters(NestedMapConverter::class)
abstract class CemedeDB : RoomDatabase() {
    abstract fun cemedeDao(): CemedeDao

    companion object {
        const val DATABASE_NAME = "CeMeDe.db"
    }
}

expect object MyDatabaseCtor : RoomDatabaseConstructor<CemedeDB>
