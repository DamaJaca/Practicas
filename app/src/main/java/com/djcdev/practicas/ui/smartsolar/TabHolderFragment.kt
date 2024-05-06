package com.djcdev.practicas.ui.smartsolar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.djcdev.practicas.databinding.FragmentTabHolderBinding
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.DetailsFragment
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.EnergyFragment
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.MiInstalacionFragment
import com.google.android.material.tabs.TabLayoutMediator

class TabHolderFragment : Fragment() {

    private var _binding : FragmentTabHolderBinding ?= null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentTabHolderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        initListeners()
    }

    private fun initListeners() {
        binding.backButtomSolar.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupViewPager() {
        val adapter = MyPagerAdapter(requireActivity())
        adapter.addFragment(MiInstalacionFragment(), "Mi Instalación")
        adapter.addFragment(EnergyFragment(), "Energía")
        adapter.addFragment(DetailsFragment(), "Detalles")
        binding.viewPagerSolar.adapter = adapter
        TabLayoutMediator(binding.tabsBar, binding.viewPagerSolar) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }
}