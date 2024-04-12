package com.djcdev.practicas.data.network.response

import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel
import com.google.gson.annotations.SerializedName

data class FacturasResponse(@SerializedName("numFacturas") val numFacturas : Int, @SerializedName("facturas") val facturas:List<FacturaResponse>)
{
    fun toDomain(): FacturasModel {
        return FacturasModel( numFacturas, facturas.map { it.toDomain() })
    }
}
data class FacturaResponse (
    @SerializedName("descEstado") val estado:String,
    @SerializedName("importeOrdenacion") val importe:Double,
    @SerializedName("fecha") val fecha:String
)
{
    fun toDomain():FacturaModel{
        return FacturaModel(estado,importe,fecha)
    }
}


