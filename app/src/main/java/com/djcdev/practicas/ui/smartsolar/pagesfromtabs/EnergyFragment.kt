package com.djcdev.practicas.ui.smartsolar.pagesfromtabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.djcdev.practicas.databinding.FragmentEnergyBinding


class EnergyFragment : Fragment() {
    private var _binging : FragmentEnergyBinding?= null
    val binding get()= _binging!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentEnergyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}