package com.djcdev.practicas.ui.facturas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentFacturasBinding
import com.djcdev.practicas.ui.facturas.adapter.FacturasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FacturasFragment : Fragment() {

    private val facturasViewModel by viewModels<FacturasViewModel>()
    private var _binding : FragmentFacturasBinding ? = null
    val binding get() = _binding!!

    private lateinit var facturasAdapter : FacturasAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        initRecyclerView()
        initUIState()
    }

    private fun initUIState() {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    facturasViewModel.facturas.collect {
                    }
                }
            }

    }

    private fun initRecyclerView() {
        facturasAdapter = FacturasAdapter()
        binding.rvFacturas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = facturasAdapter
        }
    }
}