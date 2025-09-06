package org.soumen.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.soumen.home.domain.repository.HomeRepository
import org.soumen.home.presentation.states.HomeScreenDataState
import org.soumen.shared.domain.repository.ImageDataRepository

class HomeViewModel(
    private val repository: HomeRepository,
    private val imageDataRepository: ImageDataRepository
) : ViewModel()  {

    private val _homeScreenDataState = MutableStateFlow<HomeScreenDataState>(HomeScreenDataState.Loading)
    val homeScreenDataState = _homeScreenDataState.asStateFlow()

    private val _tickerImages = MutableStateFlow<Map<String, String>>(emptyMap())
    val tickerImages = _tickerImages.asStateFlow()

    fun getGainersAndLosersData(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO + CoroutineName("HomeScreenCall")){
                repository.getTopGainersAdnLosers()
            }

            result.onSuccess {
                _homeScreenDataState.value = HomeScreenDataState.Success(it)
            }

            result.onFailure {
                _homeScreenDataState.value = HomeScreenDataState.Error(it.message ?: "Unknown Error")
            }
        }
    }


    fun getTickerImage(ticker: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO + CoroutineName("ImageFetchCall")) {
                imageDataRepository.getImageDataURL(ticker)
            }

            result.onSuccess { url ->
                _tickerImages.value = _tickerImages.value + (ticker to url)
            }
            result.onFailure {

            }
        }
    }
}