package com.djcdev.practicas.ui.facturas.filter

data class FilterModel(val fechaInicio:String, val fechaFin:String, val importeMax:Float, val pagado:Boolean, val pendientePago:Boolean)
