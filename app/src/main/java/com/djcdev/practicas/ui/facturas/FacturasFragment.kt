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
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import androidx.recyclerview.widget.LinearLayoutManager
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentFacturasBinding
import com.djcdev.practicas.domain.model.FacturaModel
import com.djcdev.practicas.ui.facturas.adapter.FacturasAdapter
import com.djcdev.practicas.ui.home.MainActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class FacturasFragment : Fragment() {

    @Inject
    lateinit var firebaseConfig: FirebaseRemoteConfig

    private val facturasViewModel by activityViewModels<FacturasViewModel>()
    private var _binding: FragmentFacturasBinding? = null
    val binding get() = _binding!!

    private lateinit var facturasAdapter: FacturasAdapter
    private var recyclerViewVisibility: Boolean = false

    private var isDataLoaded = false
    private var isMaxLoaded = false
    private var maxImport = 120f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isDataLoaded) { // Solo carga los datos la primera vez que se crea la vista
            facturasViewModel.getFacturas(false)
            isDataLoaded = true
        }
        initUi()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacturasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initUi() {
        recyclerViewVisibility = firebaseConfig.getBoolean("show_list")
        binding.rvFacturas.isVisible=recyclerViewVisibility
        initListeners()
        initUIState()

    }

    private fun initListeners() {

        binding.switchFacturas.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.progressBar.isVisible = true
            if (facturasViewModel.switchState){
                facturasViewModel.switchState=false
            }else{
                isMaxLoaded = false
                var message =if (isChecked){
                    "Mock"
                }else{"Retrofit" }
                Toast.makeText(requireContext(), "Ahora esta cargando la lista desde ${message}", Toast.LENGTH_SHORT).show()
                facturasViewModel.mock= isChecked
                facturasViewModel.getFacturas(isChecked)
            }


        }


        initNavigation(maxImport)
        binding.backButtomFacturas.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initUIState() {


        lifecycleScope.launch {
            facturasViewModel.state.collect() {
                when (it) {
                    is FacturasState.Error -> errorState()
                    is FacturasState.Loading -> loadingState()
                    is FacturasState.Success -> {
                        initRecyclerView(it.facturaModel)
                    }
                }
            }
        }

    }

    private fun loadingState() {
        binding.progressBar.isVisible = true
    }

    private fun errorState() {
        binding.progressBar.isVisible = false
        binding.tvFacturas.text = getString(R.string.error_facturas)
    }


    private fun initRecyclerView(facturas: List<FacturaModel>) {
        if (recyclerViewVisibility){
            binding.progressBar.isVisible = false
            facturasAdapter = FacturasAdapter { onItemSelected() }
            binding.rvFacturas.isVisible=true
            binding.rvFacturas.layoutManager = LinearLayoutManager(context)
            binding.rvFacturas.adapter = facturasAdapter
            // Actualiza los datos del adaptador
            facturasAdapter.updateList(facturas)
            if (!isMaxLoaded) {
                maxImport = facturasAdapter.getMaxImport()
                initNavigation(maxImport)
                isMaxLoaded = true
            }
            if ( facturas.isEmpty()){
                binding.tvList.text= getString(R.string.not_bills_response)
                binding.tvList.isVisible=true
                binding.rvFacturas.isVisible=false
            }else{
                binding.tvList.isVisible=false
                binding.rvFacturas.isVisible=true
            }
        }
        else{
            binding.rvFacturas.isVisible=false
            binding.tvList.apply {
                text=getString(R.string.not_loaded_list)
                isVisible=true
            }
        }



    }

    private fun initNavigation(maximo: Float) {
        binding.ivSettingsFacturas.setOnClickListener {
            findNavController().navigate(
                FacturasFragmentDirections.facturasFragment2(maximo)
            )

        }

    }

    private fun onItemSelected() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Información")
        builder.setMessage("Esta funcionalidad aún no está disponible")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }


}