package com.djcdev.practicas.ui.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentForgotPasswordBinding
import com.djcdev.practicas.ui.login.exceptions.FailedLogin

class ForgotPasswordFragment : Fragment() {

    private val viewModel by activityViewModels<LoginViewModel>()

    private var _binding :FragmentForgotPasswordBinding ?= null

    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentForgotPasswordBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.btnBackToLogin.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSendEmail.setOnClickListener {
            val user = binding.etUser.text.toString()
            if (user!= ""){
                viewModel.remember(user){ boolean, fail -> rememberController(boolean, fail) }
            }else{
                showErrorDialog(FailedLogin.MissingSomething)
            }
        }
    }

    private fun rememberController(boolean: Boolean, fail: FailedLogin?) {
        if (fail == null) {
            if (boolean) {
                Toast.makeText(context, "Se ha enviado un correo para recuperar su contraseña", Toast.LENGTH_SHORT).show()
                binding.pbLogin.isVisible = false
                binding.btnSendEmail.text = getString(R.string.login)

                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        } else {
            binding.pbLogin.isVisible = false
            binding.btnSendEmail.text = getString(R.string.login)
            showErrorDialog(fail)
        }
    }

    private fun showErrorDialog(fail: FailedLogin) {
        var errorMessage = ""
        errorMessage = if (fail == null) {
            "Ha ocurrido un error inesperado al intentar realizar esa acción"
        } else {
            when (fail) {
                FailedLogin.InvalidUser -> "El usuario que ingresó no existe"
                FailedLogin.InvalidPass -> ""
                FailedLogin.LoggedUser -> ""
                FailedLogin.MissingSomething -> "Introduzca un correo"
                FailedLogin.NetworkFail -> "No se ha podido conectar con el servidor"
                FailedLogin.TooManyRequests -> ""
            }
        }
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(errorMessage)
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}