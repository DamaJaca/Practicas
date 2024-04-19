package com.djcdev.practicas.ui.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentFilterBinding
import com.djcdev.practicas.ui.facturas.FacturasState
import com.djcdev.practicas.ui.facturas.FacturasViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterFragment : Fragment() {


    private var _binding: FragmentFilterBinding? = null

    private val filterViewModel by activityViewModels<FacturasViewModel>()
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
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
            filterViewModel.filterFacturas(
                pendientePago = pendientePago,
                pagado = pagado,
                importe = importeMax,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin
            )
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


    }
}
