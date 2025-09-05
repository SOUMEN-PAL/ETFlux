package org.soumen.home.presentation.states

import org.soumen.home.domain.dataModels.HomeData

sealed class HomeScreenDataState {
    object Loading : HomeScreenDataState()
    data class Success(val data : HomeData) : HomeScreenDataState()
    data class Error(val message: String) : HomeScreenDataState()
}