package com.cemede.cemede.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            sharedModule,
            useCaseModule,
            repositoryModule,
            datasourceModule,
            apiModule,
            databaseModule,
        )
    }
}
