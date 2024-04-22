package com.djcdev.practicas.ui.smartsolar.pagesfromtabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentEnergyBinding
import com.djcdev.practicas.databinding.FragmentMiInstalacionBinding


class EnergyFragment : Fragment() {
    private var _binging : FragmentEnergyBinding?= null
    val binding get()= _binging!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binging = FragmentEnergyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}