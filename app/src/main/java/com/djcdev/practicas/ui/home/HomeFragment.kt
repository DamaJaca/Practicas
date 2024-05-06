package com.djcdev.practicas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.djcdev.practicas.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.ivPractica1.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToFacturasFragment()
            )
        }
        binding.ivPractica2.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTabHolderFragment()
            )
        }
        binding.ivPractica3.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToNavigationFragment()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}