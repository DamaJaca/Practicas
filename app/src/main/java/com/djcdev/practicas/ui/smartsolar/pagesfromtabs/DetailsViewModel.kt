package com.djcdev.practicas.ui.smartsolar.pagesfromtabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djcdev.practicas.domain.model.DetailModel
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.usecase.GetDetailsUseCase
import com.djcdev.practicas.ui.facturas.FacturasState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getDetailsUseCase: GetDetailsUseCase) :ViewModel() {
    private var _details = MutableStateFlow<DetailModel>(DetailModel("","", "","","" ))
    val details: MutableStateFlow<DetailModel> get() = _details

    fun getDetails(){
        viewModelScope.launch {

            val result: DetailModel? = withContext(Dispatchers.IO) {
                getDetailsUseCase()
            }
            if (result != null) {
                _details.value = result
            } else {
                _details.value = DetailModel("Error de Carga","", "","","" )
            }
        }
    }

}