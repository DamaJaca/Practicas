package com.djcdev.practicas.ui.facturas

import com.djcdev.practicas.domain.model.FacturaModel

sealed class FacturasState {
    data object Loading:FacturasState()
    data class Error(val error:String):FacturasState()
    data class Success(val facturaModel: List<FacturaModel>):FacturasState()
}