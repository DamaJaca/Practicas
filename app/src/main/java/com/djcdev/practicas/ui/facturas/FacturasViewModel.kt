package com.djcdev.practicas.ui.facturas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.domain.usecase.FilterFacturasUseCase
import com.djcdev.practicas.domain.usecase.GetFacturasUseCase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacturasViewModel @Inject constructor(
    private val getFacturasUseCase: GetFacturasUseCase,
    private val filterFacturasUseCase: FilterFacturasUseCase
) : ViewModel() {
    private var _state = MutableStateFlow<FacturasState>(FacturasState.Loading)
    val state: StateFlow<FacturasState> get() = _state

    @Inject
    lateinit var firebaseConfig: FirebaseRemoteConfig

    var switchState : Boolean = false

    var mock : Boolean = false

    fun getFacturas(boolean: Boolean) {
        viewModelScope.launch {

            val result: List<FacturaModel> = getFacturasUseCase(boolean, firebaseConfig.getBoolean("ktor_client"))

            if (result.isNotEmpty()) {
                _state.value = FacturasState.Success(result)
            } else {
                _state.value =
                    FacturasState.Error("Error al recoger la información (FacturasViewModel)")
            }
        }
    }

    fun filterFacturas(
        pendientePago: Boolean?,
        pagado: Boolean?,
        importe: Double?,
        fechaInicio: String?,
        fechaFin: String?
    ) {
        switchState=true
        viewModelScope.launch {



                val filteredResults = filterFacturasUseCase.invoke(
                    pendientePago,
                    pagado,
                    importe,
                    fechaInicio,
                    fechaFin,
                    mock
                )

                _state.value = FacturasState.Success(filteredResults)


        }
    }
}