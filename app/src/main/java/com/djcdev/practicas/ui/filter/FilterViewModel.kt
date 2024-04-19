package com.djcdev.practicas.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel
import com.djcdev.practicas.domain.usecase.FilterFacturasUseCase
import com.djcdev.practicas.ui.facturas.FacturasState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor (private val filterFacturasUseCase: FilterFacturasUseCase): ViewModel() {

    private var _filtrado = MutableStateFlow<List<FacturaModel>>(emptyList())
    val filtrado: StateFlow<List<FacturaModel>> = _filtrado


    suspend fun filterFacturas (pendientePago:Boolean?, pagado:Boolean?, importe:Double?, fechaInicio:String?, fechaFin:String?){
        _filtrado.value= filterFacturasUseCase(pendientePago, pagado, importe, fechaInicio, fechaFin)
    }

}