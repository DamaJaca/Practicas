package com.djcdev.practicas.ui.filter

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.Locale

@AndroidEntryPoint
class FilterFragment : Fragment() {


    private var _binding: FragmentFilterBinding? = null

    private val filterViewModel by activityViewModels<FacturasViewModel>()
    val binding get() = _binding!!

    private val args: FilterFragmentArgs by navArgs()

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
        binding.sliderPriceFactura.stepSize=0.01f
        binding.sliderPriceFactura.valueTo=args.type
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
        binding.btnFromFacturasFilter.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(), {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // Create a new Calendar instance to hold the selected date
                    val selectedDate = Calendar.getInstance()
                    // Set the selected date using the values received from the DatePicker dialog
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    // Format the selected date into a string
                    val formattedDate = dateFormat.format(selectedDate.time)
                    // Update the TextView to display the selected date with the "Selected Date: " prefix
                    binding.btnFromFacturasFilter.text = formattedDate
                },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            // Show the DatePicker dialog
            datePickerDialog.show()
        }


    }

}
