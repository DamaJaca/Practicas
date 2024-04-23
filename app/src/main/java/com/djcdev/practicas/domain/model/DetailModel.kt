package com.djcdev.practicas.domain.model

import com.google.gson.annotations.SerializedName

data class DetailModel(
    val cau : String?,
    val estado : String?,
    val tipo : String?,
    val compensacion : String?,
    val potencia : String?)
