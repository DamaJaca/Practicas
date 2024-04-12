package com.djcdev.practicas.domain

import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel

interface Repository {
    suspend fun getFacturas(): List<FacturaModel>?
}