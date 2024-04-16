package com.djcdev.practicas.ui.facturas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentFacturasBinding
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.ui.facturas.adapter.FacturasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity

@AndroidEntryPoint
class FacturasFragment : Fragment() {

    private val facturasViewModel by viewModels<FacturasViewModel>()
    private var _binding : FragmentFacturasBinding ? = null
    val binding get() = _binding!!

    private lateinit var facturasAdapter : FacturasAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        facturasViewModel.getFacturas()
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
            //Crear ajustes
        }
        binding.backButtomFacturas.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initUIState() {

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    facturasViewModel.state.collect() {
                        when (it){
                            is FacturasState.Error -> errorState()
                            FacturasState.Loading -> loadingState()
                            is FacturasState.Success -> initRecyclerView(it.facturaModel)
                        }
                    }
                }
            }

    }

    private fun loadingState() {
        binding.progressBar.isVisible=true
    }

    private fun errorState() {
        binding.progressBar.isVisible=false
        binding.tvFacturas.text= getString(R.string.error_facturas)
    }

    private fun initRecyclerView(facturas : List <FacturaModel>) {

        binding.progressBar.isVisible=false
        facturasAdapter = FacturasAdapter()
        facturasAdapter.updateList(facturas)
        binding.rvFacturas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = facturasAdapter
        }


    }
}