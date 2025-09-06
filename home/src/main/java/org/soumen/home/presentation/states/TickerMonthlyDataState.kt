package org.soumen.home.presentation.states

import org.soumen.home.domain.dataModels.MonthlyTimeSeriesData

sealed class TickerMonthlyDataState {
    object Loading : TickerMonthlyDataState()
    data class Error(val e : String) : TickerMonthlyDataState()
    data class Success(val data : List<MonthlyTimeSeriesData>) : TickerMonthlyDataState()
}