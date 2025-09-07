package org.soumen.home.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.soumen.core.db.entities.WatchlistEntity
import org.soumen.home.domain.dataModels.TickerData
import org.soumen.home.domain.dataModels.WatchListData
import org.soumen.home.domain.repository.HomeRepository
import org.soumen.home.presentation.states.GainerDataState
import org.soumen.home.presentation.states.HomeScreenDataState
import org.soumen.home.presentation.states.TickerDataState
import org.soumen.home.presentation.states.TickerMonthlyDataState
import org.soumen.shared.domain.repository.ImageDataRepository

class HomeViewModel(
    private val repository: HomeRepository,
    private val imageDataRepository: ImageDataRepository
) : ViewModel()  {

    private val _homeScreenDataState = MutableStateFlow<HomeScreenDataState>(HomeScreenDataState.Loading)
    val homeScreenDataState = _homeScreenDataState.asStateFlow()

    private val _gainerListState = MutableStateFlow<GainerDataState>(GainerDataState.Loading)
    val gainerListState = _gainerListState.asStateFlow()

    private val _loserListState = MutableStateFlow<GainerDataState>(GainerDataState.Loading)
    val loserListState = _loserListState.asStateFlow()

    private val _tickerImages = MutableStateFlow<Map<String, String>>(emptyMap())
    val tickerImages = _tickerImages.asStateFlow()

    private val _tickerDataState = MutableStateFlow<TickerDataState>(TickerDataState.Loading)
    val tickerDataState = _tickerDataState.asStateFlow()

    private val _ticketMonthlyData = MutableStateFlow<TickerMonthlyDataState>(TickerMonthlyDataState.Loading)
    val tickerMonthlyData = _ticketMonthlyData.asStateFlow()

    fun getGainersAndLosersData(){
        _homeScreenDataState.value = HomeScreenDataState.Loading
        viewModelScope.launch {
            delay(100L)
            val result = withContext(Dispatchers.IO + CoroutineName("HomeScreenCall")){
                repository.getTopGainersAdnLosers()
            }

            result.onSuccess {
                it.topGainers.take(4).forEach { data ->
                    getTickerImage(data.ticker)
                }
                it.topLosers.take(4).forEach { data ->
                    getTickerImage(data.ticker)
                }
                _homeScreenDataState.value = HomeScreenDataState.Success(it)
            }

            result.onFailure {
                _homeScreenDataState.value = HomeScreenDataState.Error(it.message ?: "Unknown Error")
            }
        }
    }

    fun getAllGainers(){
        _gainerListState.value = GainerDataState.Loading
        viewModelScope.launch {
            delay(100L)
            val data = withContext(Dispatchers.IO + CoroutineName("GainerListCall")) {
               repository.getGainers()
            }

            data.onSuccess {
                withContext(Dispatchers.IO){
                    it.forEach {it->
                        getTickerImage(it.ticker)
                    }
                }
                _gainerListState.value = GainerDataState.Success(it)
            }

            data.onFailure {
                _gainerListState.value = GainerDataState.Error(it.message ?: "Unknown Error")
            }
        }
    }


    fun getAllLosers(){
        _loserListState.value = GainerDataState.Loading
        viewModelScope.launch {
            delay(100L)
            val data = withContext(Dispatchers.IO + CoroutineName("GainerListCall")) {
                repository.getLosers()
            }

            data.onSuccess {
                withContext(Dispatchers.IO){
                    it.forEach {it->
                        getTickerImage(it.ticker)
                    }
                }

                _loserListState.value = GainerDataState.Success(it)
            }

            data.onFailure {
                _loserListState.value = GainerDataState.Error(it.message ?: "Unknown Error")
            }
        }
    }





    fun getTickerImage(ticker: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO + CoroutineName("ImageFetchCall")) {
                imageDataRepository.getImageDataURL(ticker)
            }

            result.onSuccess { url ->
                Log.e("ImageTAgSuccess" , "Image URL: ${url}")
                _tickerImages.value = _tickerImages.value + (ticker to url)
            }
            result.onFailure {
                Log.e("ImageTAgFailure" , "Image Message: ${it.message}")

            }
        }
    }

    fun getTickerInfo(ticker : String){
        viewModelScope.launch {
            val result = repository.getTickerInfo(ticker)

            result.onSuccess {
                _tickerDataState.value = TickerDataState.Success(it)
            }

            result.onFailure {
                _tickerDataState.value = TickerDataState.Error(it.message ?: "Unknown Error")
            }
        }

    }

    fun getMonthlyData(ticker : String , limit : Int){
        _ticketMonthlyData.value = TickerMonthlyDataState.Loading
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO + CoroutineName("MonthlyDataCall")) {
                repository.getMonthlyData(ticker, limit)
            }

            result.onSuccess {
                _ticketMonthlyData.value = TickerMonthlyDataState.Success(it)
            }

            result.onFailure {
                _ticketMonthlyData.value = TickerMonthlyDataState.Error(it.message ?: "Unknown Error")
            }

        }
    }

    fun isBookmarked(ticker: String): StateFlow<Boolean> {
        return repository.isBookmarked(ticker)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )
    }

    fun getAllWatchlist(): StateFlow<List<WatchListData>> {
        return repository.getWatchlist()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    }


    fun addWatchList(
        watchListName : String
    ){
        viewModelScope.launch (
            Dispatchers.IO
        ){
            repository.addWatchlist(
                watchlistName = watchListName
            )
        }

    }


    fun saveToBookmark(watchlistID :Long  , ticker : org.soumen.home.domain.dataModels.Data) {
        viewModelScope.launch {
            repository.saveToBookmark(watchlistID , ticker)
        }
    }

    fun removeBookMark(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeBookmark(ticker)
        }
    }


}