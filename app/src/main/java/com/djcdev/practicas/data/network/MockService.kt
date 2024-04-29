package com.djcdev.practicas.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.djcdev.practicas.data.network.response.DetailsResponse
import com.djcdev.practicas.data.network.response.FacturasResponse
import retrofit2.http.GET

interface MockService {
    @Mock
    @MockResponse(body = "{\n" +
            "      \"cau\": \"ES002100000000001994LJF1A000\",\n" +
            "      \"estado\": \"No hemos recibido ninguna solicitud de autoconsumo\",\n" +
            "      \"tipo\": \"Con escedentes y compensación Individual-Consumo\",\n" +
            "      \"compensacion\": \"Precio PVPC\",\n" +
            "      \"potencia\": \"5kWp\"\n" +
            "    }")
    @GET("/")
    suspend fun getDetails(): DetailsResponse

    @Mock
    @MockResponse(body = "response.json")
    @GET("/")
    suspend fun getFacturas(): FacturasResponse
}