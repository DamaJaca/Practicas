package com.djcdev.practicas.data

import android.util.Log
import com.djcdev.practicas.data.database.FacturasDataBase
import com.djcdev.practicas.data.database.entities.FacturaEntity
import com.djcdev.practicas.data.network.ApiService
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService, private val facturasDataBase: FacturasDataBase) :Repository{
    override suspend fun getFacturasFromApi(): List<FacturaModel>? {
        kotlin.runCatching {
            apiService.getFacturas()
        }.onSuccess { return it.toDomain().facturas }
            .onFailure { Log.i("ERROR-TAG", it.message.toString())}
        return null
    }

    override suspend fun getFacturasFromDatabase(): List<FacturaModel>? {
        kotlin.runCatching {
            facturasDataBase.getFacturasDao()
        }.onSuccess { database -> return database.getAllFacturas().map { it.toDomain() } }
            .onFailure { Log.i("ERROR-TAG", it.message.toString()) }
        return null
    }

    override suspend fun insertFacturas(facturas: List<FacturaEntity>) {
        kotlin.runCatching {
            facturasDataBase.getFacturasDao()
        }.onSuccess {
            it.insertAll(facturas)
        }.onFailure { Log.i("ERROR-TAG", it.message.toString()) }
    }

    override suspend fun clearAll() {
        kotlin.runCatching {
            facturasDataBase.getFacturasDao()
        }.onSuccess {
            it.deleteAll()
        }.onFailure { Log.i("ERROR-TAG", it.message.toString()) }
    }


}