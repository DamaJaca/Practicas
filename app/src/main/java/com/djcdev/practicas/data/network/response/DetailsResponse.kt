package com.djcdev.practicas.data.network.response

import com.djcdev.practicas.domain.model.DetailModel
import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    @SerializedName("cau") val cau : String,
    @SerializedName("estado") val estado : String,
    @SerializedName("tipo") val tipo : String,
    @SerializedName("compensacion") val compensacion : String,
    @SerializedName("potencia") val potencia : String)
{
    fun toDomain(): DetailModel {
        return DetailModel( cau, estado, tipo, compensacion, potencia)
    }
}