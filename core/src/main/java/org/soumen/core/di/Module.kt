package org.soumen.core.di

import androidx.room.Room
import org.koin.dsl.module
import org.soumen.core.db.AppDataBase

val coreModule = module {
    single<AppDataBase> {
        Room.databaseBuilder(
            get(),
            AppDataBase::class.java,
            "appDatabase"
        ).build()
    }

    single { get<AppDataBase>().imageDao() }
}