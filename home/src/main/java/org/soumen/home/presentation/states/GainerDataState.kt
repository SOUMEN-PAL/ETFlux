package org.soumen.home.presentation.states

import org.soumen.home.domain.dataModels.Data

sealed class GainerDataState {
    object Loading : GainerDataState()
    data class Error(val e : String) : GainerDataState()
    data class Success(val data : List<Data>) : GainerDataState()
}