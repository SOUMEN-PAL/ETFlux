package org.soumen.home.presentation.states

import org.soumen.home.domain.dataModels.TickerData

sealed class TickerDataState {
    object Loading : TickerDataState()
    data class Error(val e : String) : TickerDataState()
    data class Success(val data : TickerData) : TickerDataState()
}