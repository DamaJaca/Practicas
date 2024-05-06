package com.djcdev.practicas.ui.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.djcdev.practicas.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment() {
    companion object{
        const val URL_DESTINO = "https://www.iberdrola.es"
     }

    private var _binding : FragmentNavigationBinding ?= null

    val binding get ()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.backButtomFacturas.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnExternalNavigation.setOnClickListener {
            binding.webView.isVisible=false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_DESTINO))
            startActivity(intent)
        }
        binding.btnInternalNavigation.setOnClickListener {
            binding.webView.apply {
                isVisible=true
                clearCache(true)
                webViewClient= WebViewClient()
                loadUrl(URL_DESTINO)
            }
        }
    }
}