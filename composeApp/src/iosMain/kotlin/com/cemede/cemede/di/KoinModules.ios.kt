package com.cemede.cemede.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.cemede.cemede.data.data_base.CemedeDB
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val platformModule =
    module {
        // HttpClientEngine
        single<HttpClientEngine> {
            Darwin.create()
        }
        // Room database, CemedeDatabase
        single<CemedeDB> {
            Room
                .databaseBuilder<CemedeDB>(
                    name = NSHomeDirectory() + "/${CemedeDB.DATABASE_NAME}",
                ).setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
//            .fallbackToDestructiveMigration(true)
                .build()
        }
    }
