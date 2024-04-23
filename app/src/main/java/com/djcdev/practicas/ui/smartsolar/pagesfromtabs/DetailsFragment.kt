package com.djcdev.practicas.ui.smartsolar.pagesfromtabs

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentDetailsBinding
import com.djcdev.practicas.databinding.FragmentEnergyBinding
import com.djcdev.practicas.domain.model.DetailModel
import com.djcdev.practicas.domain.usecase.GetDetailsUseCase
import com.djcdev.practicas.ui.facturas.FacturasState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binging: FragmentDetailsBinding? = null
    val binding get() = _binging!!

    private val detailsViewModel by activityViewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

    }

    private fun initUI() {
        detailsViewModel.getDetails()
        lifecycleScope.launch {
            detailsViewModel.details.collect() {
                requireActivity().runOnUiThread {
                    binding.etCau.text = it.cau
                    binding.etCompensacion.text = it.compensacion
                    binding.etEstado.text = it.estado
                    binding.etTipo.text = it.tipo
                    binding.etPotenia.text = it.potencia
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binging = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}