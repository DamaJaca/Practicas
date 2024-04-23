package com.djcdev.practicas.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djcdev.practicas.databinding.FragmentSingupBinding


class SingupFragment : Fragment() {

    private var _binding :FragmentSingupBinding ?= null

    val binding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingupBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}