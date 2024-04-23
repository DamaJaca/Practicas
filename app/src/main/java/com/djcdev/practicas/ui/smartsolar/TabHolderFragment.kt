package com.djcdev.practicas.ui.smartsolar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.djcdev.practicas.databinding.FragmentTabHolderBinding
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.DetailsFragment
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.EnergyFragment
import com.djcdev.practicas.ui.smartsolar.pagesfromtabs.MiInstalacionFragment
import com.google.android.material.tabs.TabLayout

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
        val adapter = MyPagerAdapter(childFragmentManager)
        adapter.addFragment(MiInstalacionFragment(), "Mi Instalación")
        adapter.addFragment(EnergyFragment(), "Energía")
        adapter.addFragment(DetailsFragment(), "Detalles")
        binding.viewPagerSolar.adapter = adapter
        binding.tabsBar.setupWithViewPager(binding.viewPagerSolar)
    }
}