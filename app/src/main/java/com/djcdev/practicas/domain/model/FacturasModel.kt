package com.djcdev.practicas.domain.model


data class FacturasModel(val numFacturas : Int, val facturas:List<FacturaModel>)

data class FacturaModel (
    val estado:String,
    val importe:Double,
    val fecha:String
)
