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
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single { get<AppDataBase>().imageDao() }

    single { get<AppDataBase>().gainersLosersDao() }

    single { get<AppDataBase>().tickerInfoDao() }

    single { get<AppDataBase>().monthlyStockEntityDao() }

    single { get<AppDataBase>().watchlistEntityDao() }

    single { get<AppDataBase>().bookmarkEntityDao() }
}