package org.soumen.etflux.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.soumen.core.di.coreModule
import org.soumen.home.di.HomeModule
import org.soumen.shared.di.sharedModule

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(
            mainModule,
            HomeModule,
            sharedModule,
            coreModule
        )
    }
}