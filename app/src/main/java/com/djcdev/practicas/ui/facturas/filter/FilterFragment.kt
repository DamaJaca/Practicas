package com.djcdev.practicas.ui.facturas.filter

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentFilterBinding
import com.djcdev.practicas.ui.facturas.FacturasState
import com.djcdev.practicas.ui.facturas.FacturasViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "filters")

@AndroidEntryPoint
class FilterFragment : Fragment() {


    private var _binding: FragmentFilterBinding? = null

    private val filterViewModel by activityViewModels<FacturasViewModel>()
    val binding get() = _binding!!

    private val args: FilterFragmentArgs by navArgs()
    var condicion: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        condicion = true
        initUI()
    }

    private fun initUI() {
        initListeners()
        binding.sliderPriceFactura.stepSize = 0.01f
        binding.sliderPriceFactura.valueTo = args.type
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { condicion }.collect {
                if (it != null) {
                    requireActivity().runOnUiThread {
                        binding.cbPaid.isChecked = it.pagado
                        binding.cbPendientePago.isChecked = it.pendientePago
                        binding.sliderPriceFactura.value =
                            String.format("%.2f", it.importeMax).toFloat()
                        binding.btnFromFacturasFilter.text = it.fechaInicio
                        binding.btnToFacturasFilter.text = it.fechaFin
                    }
                    condicion = false
                }
            }
        }
    }


    private fun initListeners() {
        binding.btnCloseFilterFacturas.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnClearFilters.setOnClickListener {
            binding.cbCuotaFija.isChecked = false
            binding.cbNulled.isChecked = false
            binding.cbPaid.isChecked = false
            binding.cbPayPlan.isChecked = false
            binding.cbPendientePago.isChecked = false
            binding.sliderPriceFactura.value = 0.0f
            binding.btnFromFacturasFilter.text = getString(R.string.date_filter)
            binding.btnToFacturasFilter.text = getString(R.string.date_filter)

            //Safe al limpiar
            CoroutineScope(Dispatchers.IO).launch {
                safeFechaFin(getString(R.string.date_filter))
                safeFechaInicio(getString(R.string.date_filter))
                safeChecks("pagado", false)
                safeChecks("pendientePago", false)
                safeImporte(0.0f)
            }
        }
        binding.btnApplyFilter.setOnClickListener {
            var pagado: Boolean? = null
            var pendientePago: Boolean? = null
            var importeMax: Double? = null
            var fechaInicio: String? = null
            var fechaFin: String? = null
            if (binding.cbPendientePago.isChecked) {
                pendientePago = true
            }
            if (binding.cbPaid.isChecked) {
                pagado = true
            }
            if (binding.sliderPriceFactura.value > 0) {
                importeMax = binding.sliderPriceFactura.value.toDouble()
            }
            if (binding.btnToFacturasFilter.text != getString(R.string.date_filter)) {
                fechaFin = binding.btnToFacturasFilter.text.toString()
            }
            if (binding.btnFromFacturasFilter.text != getString(R.string.date_filter)) {

                fechaInicio = binding.btnFromFacturasFilter.text.toString()
            }

            //Safe antes de aplicar cambios
            CoroutineScope(Dispatchers.IO).launch {
                if (fechaInicio != null) {
                    safeFechaInicio(fechaInicio)
                } else {
                    safeFechaInicio(getString(R.string.date_filter))
                }
                if (fechaFin != null) {
                    safeFechaFin(fechaFin)
                } else {
                    safeFechaFin(getString(R.string.date_filter))
                }
                if (importeMax != null) {
                    safeImporte(importeMax.toFloat())
                } else {
                    safeImporte(0.0f)
                }
                safeChecks("pendientePago", pendientePago ?: false)
                safeChecks("pagado", pagado ?: false)
            }
            filterViewModel.filterFacturas(
                pendientePago = pendientePago,
                pagado = pagado,
                importe = importeMax,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin
            )
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnFromFacturasFilter.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    if (binding.btnToFacturasFilter.text == getString(R.string.date_filter)) {
                        binding.btnToFacturasFilter.text =
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date()
                            )
                    }
                    CoroutineScope(Dispatchers.IO).launch { safeFechaInicio(formattedDate) }
                    binding.btnFromFacturasFilter.text = formattedDate
                },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.btnToFacturasFilter.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    if (binding.btnFromFacturasFilter.text == getString(R.string.date_filter)) {
                        binding.btnFromFacturasFilter.text = formattedDate
                    }
                    CoroutineScope(Dispatchers.IO).launch { safeFechaFin(formattedDate) }
                    binding.btnToFacturasFilter.text = formattedDate
                },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }


    }

    private suspend fun safeFechaInicio(date: String) {
        requireContext().dataStore.edit { preferences ->
            preferences[stringPreferencesKey("fechaInicio")] = date
        }
    }

    private suspend fun safeFechaFin(date: String) {
        requireContext().dataStore.edit { preferences ->
            preferences[stringPreferencesKey("fechaFin")] = date
        }
    }

    private suspend fun safeImporte(importe: Float) {
        requireContext().dataStore.edit { preferences ->
            preferences[floatPreferencesKey("importeMax")] = importe
        }
    }

    private suspend fun safeChecks(key: String, boolean: Boolean) {
        requireContext().dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = boolean
        }
    }

    private fun getSettings(): Flow<FilterModel?> {
        return requireContext().dataStore.data.map {
            FilterModel(
                it[stringPreferencesKey("fechaInicio")] ?: getString(R.string.date_filter),
                it[stringPreferencesKey("fechaFin")] ?: getString(R.string.date_filter),
                it[floatPreferencesKey("importeMax")] ?: 0.0f,
                it[booleanPreferencesKey("pagado")] ?: false,
                it[booleanPreferencesKey("pendientePago")] ?: false
            )
        }


    }


}
