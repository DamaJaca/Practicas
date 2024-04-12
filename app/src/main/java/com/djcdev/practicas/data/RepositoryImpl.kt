package com.djcdev.practicas.data

import android.util.Log
import com.djcdev.practicas.data.network.ApiService
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) :Repository{
    override suspend fun getFacturas(): List<FacturaModel>? {
        kotlin.runCatching {
            apiService.getFacturas()
        }.onSuccess { return it.toDomain().facturas }
            .onFailure { Log.i("ERROR-TAG", it.message.toString())}
        return null
    }

}