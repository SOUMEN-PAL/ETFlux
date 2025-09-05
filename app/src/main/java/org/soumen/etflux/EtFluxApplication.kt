package org.soumen.etflux

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.soumen.etflux.di.initKoin

class EtFluxApplication : Application() {

    companion object {
        lateinit var instance: EtFluxApplication
            private set // Make the setter private to ensure it's only set internally

        // Optional: A more direct way to get context if needed, but instance is often preferred
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
        initKoin {
            androidContext(appContext)
        }

    }


}