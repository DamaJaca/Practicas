package com.djcdev.practicas.domain.usecase

import android.util.Log
import com.djcdev.practicas.domain.Repository
import com.djcdev.practicas.domain.model.FacturaModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FilterFacturasUseCase @Inject constructor (private val repository: Repository) {
    suspend operator fun invoke(
        pendientePago: Boolean?,
        pagada: Boolean?,
        importeMax: Double?,
        fechaInicio: String?,
        fechaFin: String?
    ): List<FacturaModel> {
        var facturas: List<FacturaModel> = repository.getFacturasFromDatabase()!!


        var facturasFiltradas: MutableList<FacturaModel> = mutableListOf()

        var facturasFinal: MutableList<FacturaModel> = mutableListOf()

        //Comprobamos las que están pendientes de pago
        if (pendientePago != null) {
            facturasFiltradas.clear()
            facturasFiltradas = facturas.filter { it.estado == "Pendiente de pago" }.toMutableList()
            facturasFinal.addAll(facturasFiltradas)
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


        //Ahora, ultimo filtrado por fecha
        if (fechaInicio != null && fechaFin != null) {
            //Si no se ha hecho ningun filtro previo:
            if (pagada == null && pendientePago == null && importeMax == null) {
                facturas.map {if (compararFechas(it.fecha, fechaFin)<0 &&
                    compararFechas(it.fecha, fechaInicio)>0
                ) {
                    facturasFinal.add(it)
                }
                }
            }else{
                facturasFiltradas.clear()
                facturasFiltradas.addAll(facturasFinal)
                facturasFinal.clear()
                facturasFiltradas.map{
                    if (compararFechas(it.fecha, fechaFin)<0 &&
                        compararFechas(it.fecha, fechaInicio)>0
                    ) {
                        facturasFinal.add(it)
                    }
                }
            }
        }

        return if (importeMax==null && fechaFin ==null
            && fechaInicio==null && pagada ==null && pendientePago==null){
            facturas
        } else{
            facturasFinal
        }




    }


    fun compararFechas(fecha1: String, fecha2: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date1 = dateFormat.parse(fecha1)!!
            val date2 = dateFormat.parse(fecha2)
            date1.compareTo(date2)
        } catch (e: Exception) {
            0
        }
    }
}