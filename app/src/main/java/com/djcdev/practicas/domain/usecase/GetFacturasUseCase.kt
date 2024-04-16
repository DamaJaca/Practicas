package com.djcdev.practicas.domain.usecase

import android.util.Log
import android.widget.Toast
import com.djcdev.practicas.data.database.entities.FacturaEntity
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository :Repository){

    suspend operator fun invoke () :List<FacturaModel>? {
        var facturas: List<FacturaModel>? = repository.getFacturasFromApi()
        if (facturas!=null){
            if(facturas.isNotEmpty()){
                repository.clearAll()
                repository.insertFacturas(facturas.map { it.toDatabase() })
            }else {
                facturas = repository.getFacturasFromDatabase() ?: emptyList()
            }
        }else{
            facturas = repository.getFacturasFromDatabase() ?: emptyList()
        }
        return facturas

    }


}