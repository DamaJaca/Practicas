package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository :Repository){

    suspend operator fun invoke (boolean: Boolean) :List<FacturaModel> {
        if (!boolean){
            var facturas: List<FacturaModel>? = repository.getFacturasFromApi()
            if (facturas!=null){
                if(facturas.isNotEmpty()){
                    repository.clearAll()
                    repository.insertFacturas(facturas.map { it.toDatabase() })
                }
//                else {
//                    facturas = repository.getFacturasFromDatabase() ?: emptyList()
//                }
            }else{
                facturas = repository.getFacturasFromDatabase() ?: emptyList()
            }
            return facturas
        }
        else {
            return repository.getFacturasFromMock() ?: emptyList()

        }

    }


}