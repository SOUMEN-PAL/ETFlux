package org.soumen.home.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.soumen.home.data.networking.api.HomeScreenApiService
import org.soumen.home.domain.repository.HomeRepository
import org.soumen.home.presentation.viewmodels.HomeViewModel

val HomeModule = module {
    single {
        HomeScreenApiService()
    }

    single {
        HomeRepository(
            apiService = get<HomeScreenApiService>(),
            gainersLosersEntityDao = get(),
            tickerInfoApiService = get(),
            tickerEntityDao = get(),
            monthlyStockEntityDao = get(),
            watchListEntityDao = get(),
            bookmarkEntityDao = get(),
        )
    }

    viewModel {
        HomeViewModel(
            repository = get(),
            imageDataRepository = get()
        )
    }
}