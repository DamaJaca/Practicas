package com.djcdev.practicas.ui.facturas

import com.djcdev.practicas.domain.model.FacturaModel

sealed class FacturasState {
    data object Loading:FacturasState()
    data class Error(val error:String):FacturasState()
    data class Success(var facturaModel: List<FacturaModel>):FacturasState(){
        fun setFacturas(facturaModel: List<FacturaModel>){
            this.facturaModel=facturaModel
        }
    }
}