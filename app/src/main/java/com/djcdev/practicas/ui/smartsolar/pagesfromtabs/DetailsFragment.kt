package com.djcdev.practicas.ui.smartsolar.pagesfromtabs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.djcdev.practicas.databinding.DialogDetailsBinding
import com.djcdev.practicas.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binging: FragmentDetailsBinding? = null
    val binding get() = _binging!!

    private lateinit var dialogBinding : DialogDetailsBinding

    lateinit var dialog : Dialog

    private val detailsViewModel by activityViewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()

    }

    private fun initListeners() {
        binding.ivInfoButtom.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {

        dialogBinding.btnAceptarDialog.setOnClickListener {
            dialog.hide()
        }

        dialog.show()
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
                    if (it.cau != "") {
                        binding.pbSolar.isVisible=false
                        binding.linearSolarFragment.isVisible = true
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog = Dialog (requireActivity())

        dialogBinding = DialogDetailsBinding.inflate(layoutInflater, container,false)
        _binging = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        dialog.setContentView(dialogBinding.root)
        return binding.root
    }
}