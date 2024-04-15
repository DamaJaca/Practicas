package com.djcdev.practicas.domain.model

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


data class FacturasModel @Inject constructor(val numFacturas : Int, val facturas:List<FacturaModel>)

data class FacturaModel (
    val estado:String,
    val importe:Double,
    val fecha:String
)
