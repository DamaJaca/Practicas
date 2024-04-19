package com.djcdev.practicas.ui.facturas

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentFacturasBinding
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.ui.facturas.adapter.FacturasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FacturasFragment : Fragment() {

    private val facturasViewModel by activityViewModels<FacturasViewModel>()
    private var _binding : FragmentFacturasBinding ? = null
    val binding get() = _binding!!

    private lateinit var facturasAdapter : FacturasAdapter

    private var isDataLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isDataLoaded) { // Solo carga los datos la primera vez que se crea la vista
            facturasViewModel.getFacturas()
            Log.d("paco", "Ha entrado al get facturas")
            isDataLoaded = true
        }
        initUi()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacturasBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    private fun initUi() {
        initListeners()
        initUIState()

    }

    private fun initListeners() {
        binding.ivSettingsFacturas.setOnClickListener{
            findNavController().navigate(
                FacturasFragmentDirections.facturasFragment2()
            )
        }
        binding.backButtomFacturas.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initUIState() {


            lifecycleScope.launch {
                //repeatOnLifecycle(Lifecycle.State.STARTED) {
                    facturasViewModel.state.collect() {
                        when (it){
                            is FacturasState.Error -> errorState()
                            is FacturasState.Loading -> loadingState()
                            is FacturasState.Success -> {
                                Log.i("Paco", "en el collect= ${it.facturaModel.toString()}")

                                initRecyclerView(it.facturaModel)}
                        }
                    }
                //}
            }

    }

    private fun loadingState() {
        binding.progressBar.isVisible=true
    }

    private fun errorState() {
        binding.progressBar.isVisible=false
        binding.tvFacturas.text= getString(R.string.error_facturas)
    }


    private fun initRecyclerView(facturas: List<FacturaModel>) {
            binding.progressBar.isVisible=false
            facturasAdapter = FacturasAdapter { onItemSelected() }
            binding.rvFacturas.layoutManager = LinearLayoutManager(context)
            binding.rvFacturas.adapter = facturasAdapter
        Log.i("Paco", "en el recyclerview= ${facturas.toString()}")
        // Actualiza los datos del adaptador
        facturasAdapter.updateList(facturas)
    }

    private fun onItemSelected() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_factura_information)
        val btnClose : Button= dialog.findViewById(R.id.btnCloseDialogFacturas)
        btnClose.setOnClickListener{
            dialog.hide()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }


}