package com.djcdev.practicas.data.network.response

import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel
import kotlinx.serialization.Serializable

@Serializable
data class FacturasHtmlResponse(val numFacturas : Int, val facturas:List<FacturaHtmlResponse>){
    fun toDomain(): FacturasModel {
        return FacturasModel( numFacturas, facturas.map { it.toDomain() })
    }
}
@Serializable
data class FacturaHtmlResponse (
    val descEstado:String,
    val importeOrdenacion:Double,
    val fecha:String
)
{
    fun toDomain(): FacturaModel {
        return FacturaModel(descEstado,importeOrdenacion,fecha)
    }
}
