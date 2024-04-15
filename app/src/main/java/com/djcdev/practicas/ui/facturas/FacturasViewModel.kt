package com.djcdev.practicas.ui.facturas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.model.FacturasModel
import com.djcdev.practicas.domain.usecase.GetFacturasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FacturasViewModel @Inject constructor( private val getFacturasUseCase: GetFacturasUseCase) :ViewModel() {
    private var _state = MutableStateFlow<FacturasState>(FacturasState.Loading)
    val state: StateFlow<FacturasState> = _state

    fun getFacturas (){
        viewModelScope.launch {

            val result: List<FacturaModel>? = withContext(Dispatchers.IO){getFacturasUseCase()}
            if (result!= null){
                _state.value = FacturasState.Success(result)
            }
            else{
                _state.value=FacturasState.Error("Error al recoger la información (FacturasViewModel)")
            }
        }
    }
}