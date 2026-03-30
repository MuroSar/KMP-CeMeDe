package com.cemede.cemede.data.data_base

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.cemede.cemede.data.data_base.converter.LocalDateConverter
import com.cemede.cemede.data.data_base.converter.LocalTimeConverter
import com.cemede.cemede.data.data_base.converter.PartnersScheduleConverter
import com.cemede.cemede.data.data_base.converter.ScheduleTypeConverter
import com.cemede.cemede.data.data_base.converter.StaffMemberWorkingScheduleConverter
import com.cemede.cemede.data.data_base.converter.WorkingScheduleConverter
import com.cemede.cemede.data.data_base.dao.CemedeDao
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.data_base.model.StaffMemberEntity

@Database(
    entities = [
        StaffMemberEntity::class,
        PartnerEntity::class,
    ],
    version = 1,
)
@ConstructedBy(MyDatabaseCtor::class)
@TypeConverters(
    LocalDateConverter::class,
    LocalTimeConverter::class,
    PartnersScheduleConverter::class,
    ScheduleTypeConverter::class,
    StaffMemberWorkingScheduleConverter::class,
    WorkingScheduleConverter::class,
)
abstract class CemedeDB : RoomDatabase() {
    abstract fun cemedeDao(): CemedeDao

    companion object {
        const val DATABASE_NAME = "CeMeDe.db"
    }
}

expect object MyDatabaseCtor : RoomDatabaseConstructor<CemedeDB>
