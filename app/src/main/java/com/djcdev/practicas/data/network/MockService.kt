package com.djcdev.practicas.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.djcdev.practicas.data.network.response.DetailsResponse
import com.djcdev.practicas.data.network.response.FacturasResponse
import retrofit2.http.GET

interface MockService {
    @Mock
    @MockResponse(body = "details.json")
    @GET("/")
    suspend fun getDetails(): DetailsResponse

    @Mock
    @MockResponse(body = "response.json")
    @GET("/")
    suspend fun getFacturas(): FacturasResponse
}