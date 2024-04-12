package com.djcdev.practicas.ui.facturas

import androidx.lifecycle.ViewModel
import com.djcdev.practicas.domain.model.FacturasModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FacturasViewModel @Inject constructor( private val facturasModel: FacturasModel) :ViewModel() {
    private var _facturas = MutableStateFlow<List<FacturasModel>>(emptyList())
    val facturas :StateFlow<List<FacturasModel>> =_facturas

    init {
        _facturas.value= facturas.value
    }
}