package com.djcdev.practicas.domain.usecase

import android.util.Log
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class FilterFacturasUseCase @Inject constructor (private val repository: Repository) {
    suspend operator fun invoke(
        pendientePago: Boolean?,
        pagada: Boolean?,
        importeMax: Double?,
        fechaInicio: String?,
        fechaFin: String?
    ): List<FacturaModel> {
        Log.i("Paco", "ha entrado en la funcion del caso de uso")
        var facturas: List<FacturaModel> = repository.getFacturasFromDatabase()!!

        Log.i("Paco", "la base de datos ha devuelto: ${facturas.toString()}")

        var facturasFiltradas: MutableList<FacturaModel> = mutableListOf()

        var facturasFinal: MutableList<FacturaModel> = mutableListOf()

        //Comprobamos las que están pendientes de pago
        if (pendientePago != null) {
            facturasFiltradas.clear()
            facturasFiltradas = facturas.filter { it.estado == "Pendiente de pago" }.toMutableList()
            facturasFinal.addAll(facturasFiltradas)
            Log.i("Paco", "ha despues del filtro de pendiente de pago= ${facturasFinal.toString()}")
        }

        //Comprobamos las que estan pagadas
        if (pagada != null) {
            facturasFiltradas.clear()
            facturasFiltradas = facturas.filter { it.estado == "Pagada" }.toMutableList()
            facturasFinal.addAll(facturasFiltradas)
        }
        //Comprobamos el importe máximo, si no es nulo comprobamos si se ha filtrado primero por otro parámetro de checkbox para hacerle el filtro a eso
        if (importeMax != null) {
            if (pagada == null || pendientePago == null) {
                facturasFiltradas = facturas.filter { it.importe <= importeMax }.toMutableList()
                facturasFinal.addAll(facturasFiltradas)
            }//En caso de que se haya hecho un filtro previo se realiza un filtro a la lista previa
            else {
                facturasFiltradas =
                    facturasFinal.filter { it.importe <= importeMax }.toMutableList()
                facturasFinal.clear()
                facturasFinal.addAll(facturasFiltradas)
            }

        }

        Log.i("Paco", "ha filtrado todo hasta la fecha")

        //Ahora, ultimo filtrado por fecha
        if (fechaInicio != null && fechaFin != null) {
            //Si no se ha hecho ningun filtro previo:
            if (pagada == null && pendientePago == null && importeMax == null) {
                facturas.map {
                    if (stringToDate(it.fecha) < stringToDate(fechaFin) &&
                        stringToDate(it.fecha) > stringToDate(fechaInicio)
                    ) {
                        facturasFinal.add(it)
                    }
                }
            }else{
                facturasFiltradas.clear()
                facturasFiltradas.addAll(facturasFinal)
                facturasFinal.clear()
                facturasFiltradas.map{
                    if (stringToDate(it.fecha) < stringToDate(fechaFin) &&
                        stringToDate(it.fecha) > stringToDate(fechaInicio)
                    ) {
                        facturasFinal.add(it)
                    }
                }
            }
        }
        Log.i("Paco", "ha filtrado la fecha ${facturasFinal.toString()}")


        return facturasFinal

    }

    private fun stringToDate(fecha: String): Date {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.parse(fecha)!!
    }
}