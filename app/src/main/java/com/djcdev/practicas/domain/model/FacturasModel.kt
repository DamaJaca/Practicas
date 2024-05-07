package com.djcdev.practicas.domain.model

import com.djcdev.practicas.data.database.entities.FacturaEntity
import javax.inject.Inject


data class FacturasModel @Inject constructor(val numFacturas : Int, val facturas:List<FacturaModel>)

data class FacturaModel (
    val estado:String,
    val importe:Double,
    val fecha:String
)
{
    fun toDatabase():FacturaEntity{
        return FacturaEntity(estado=estado,importe=importe,fecha=fecha)
    }
}
