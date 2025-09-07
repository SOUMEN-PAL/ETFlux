package org.soumen.watchlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.soumen.core.db.entities.WatchlistEntity
import org.soumen.watchlist.domain.WatchlistRepository
import org.soumen.watchlist.domain.datamodules.WatchlistData
import org.soumen.watchlist.presentation.states.WatchListDataState

class WatchlistViewModel(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _watchListDataState = MutableStateFlow<WatchListDataState>(WatchListDataState.Loading)
    val watchListDataState = _watchListDataState.asStateFlow()

    val watchlists: StateFlow<List<WatchlistData>> =
        watchlistRepository.getWatchList()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    private fun WatchlistEntity.toWatchlistData(): WatchlistData  = WatchlistData(
        id = this.watchlistId,
        name = this.watchlistName
    )

    fun deleteWatchlist(id: Long) {
        viewModelScope.launch {
            watchlistRepository.deleteWatchList(id)
        }
    }

    fun getAllItemsInWatchlist(id : Long){
        _watchListDataState.value = WatchListDataState.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO + CoroutineName("Get Bookmarks for watchlist")){
                watchlistRepository.getAllBookmarksWithWatchlistID(id)
            }

            result.onSuccess {
                if(it.isNotEmpty()){
                    _watchListDataState.value = WatchListDataState.Success(it)
                }else{
                    _watchListDataState.value = WatchListDataState.Empty
                }
            }

            result.onFailure {
                _watchListDataState.value = WatchListDataState.Error
            }
        }
    }


}