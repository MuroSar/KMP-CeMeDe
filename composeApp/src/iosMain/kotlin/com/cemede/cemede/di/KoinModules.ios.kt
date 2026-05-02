package com.cemede.cemede.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.cemede.cemede.data.data_base.CemedeDB
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
// Importaciones necesarias para las rutas de iOS
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {
    // HttpClientEngine
    single<HttpClientEngine> {
        Darwin.create()
    }

    // Room database
    single<CemedeDB> {
        // 1. Buscamos la ruta legal para escribir archivos (Documents)
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        // 2. Armamos el path completo: Documents/Cemede.db
        val dbFilePath = documentDirectory?.path + "/" + CemedeDB.DATABASE_NAME

        Room.databaseBuilder<CemedeDB>(
            name = dbFilePath, // <--- CAMBIAMOS NSHomeDirectory por dbFilePath
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
