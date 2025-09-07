package org.soumen.watchlist.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.soumen.watchlist.domain.WatchlistRepository
import org.soumen.watchlist.presentation.viewmodel.WatchlistViewModel

val watchlistModule = module {
    single {
        WatchlistRepository(get(), get())
    }

    viewModel {
        WatchlistViewModel(
            watchlistRepository = get()
        )
    }
}