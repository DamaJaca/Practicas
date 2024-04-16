package com.djcdev.practicas.domain

import com.djcdev.practicas.data.database.entities.FacturaEntity
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel

interface Repository {
    suspend fun getFacturasFromApi(): List<FacturaModel>?

    suspend fun getFacturasFromDatabase(): List<FacturaModel>?
    suspend fun insertFacturas(facturas:List<FacturaEntity>)
    suspend fun clearAll()
}