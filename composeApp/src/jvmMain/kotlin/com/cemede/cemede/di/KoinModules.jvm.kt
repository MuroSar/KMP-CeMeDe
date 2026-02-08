package com.cemede.cemede.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.cemede.cemede.data.data_base.CemedeDB
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import java.io.File

actual val platformModule =
    module {

        // HttpClientEngine
        single<HttpClientEngine> {
            OkHttp.create()
        }
        // Room database, CemedeDatabase
        single<CemedeDB> {
            Room
                .databaseBuilder<CemedeDB>(
                    name = File(System.getProperty("java.io.tmpdir"), CemedeDB.DATABASE_NAME).absolutePath,
                ).setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
//            .fallbackToDestructiveMigration(true)
                .build()
        }
    }
