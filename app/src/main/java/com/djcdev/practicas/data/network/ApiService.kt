package com.djcdev.practicas.data.network

import com.djcdev.practicas.data.network.response.FacturasResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/facturas")
    suspend fun getFacturas():FacturasResponse
}