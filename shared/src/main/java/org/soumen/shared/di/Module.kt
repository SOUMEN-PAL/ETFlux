package org.soumen.shared.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.soumen.core.api.ImageApiService
import org.soumen.shared.data.network.ImageService
import org.soumen.shared.data.network.TickerInfoApiService
import org.soumen.shared.domain.repository.ImageDataRepository

val sharedModule : Module = module {
    single {
        ImageService()
    }
    single {
        TickerInfoApiService()
    }

    single {
        ImageDataRepository(
            imageService = get(),
            imageDao = get(),
            tickerInfoApiService = get()
        )
    }
}