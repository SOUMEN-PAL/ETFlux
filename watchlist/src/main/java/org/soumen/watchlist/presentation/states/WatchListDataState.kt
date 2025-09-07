package org.soumen.watchlist.presentation.states

import org.soumen.home.domain.dataModels.Data
import org.soumen.watchlist.domain.datamodules.WatchlistData

sealed class WatchListDataState {
    object Loading : WatchListDataState()
    object Empty : WatchListDataState()
    object Error : WatchListDataState()
    data class Success(val data : List<Data>) : WatchListDataState()
}