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
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentSingupBinding


class SingupFragment : Fragment() {


    private val viewModel by activityViewModels<LoginViewModel>()

    private var _binding :FragmentSingupBinding ?= null

    val binding get()=_binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners() {
        binding.btnBackToLogin.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnRegister.setOnClickListener {
            binding.btnRegister.text= ""
            binding.pbSignUp.isVisible=true
            val user = binding.etUser.text.toString()
            val pass = binding.etPass.text.toString()
            viewModel.singUp(user, pass){bolean, fail -> signUpController(bolean, fail)}

        }
    }

    private fun signUpController(boolean: Boolean, fail:FailedSignUp?) {
        if (fail!=null){
            if (boolean){
                Toast.makeText(context, "Registro realizado con exito", Toast.LENGTH_SHORT).show()
                binding.pbSignUp.isVisible=false
                binding.btnRegister.text= getString(R.string.singup)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }else{
                binding.pbSignUp.isVisible=false
                binding.btnRegister.text= getString(R.string.singup)
            }
        }
        showErrorDialog(fail)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingupBinding.inflate(layoutInflater,container,false)
        return binding.root
    }



    private fun showErrorDialog(fail: FailedSignUp?){
        var errorMessage = ""
        errorMessage = if (fail==null){
            "Ha ocurrido un error inesperado al intentar realizar esa acción"
        } else{
            when(fail){
                FailedSignUp.InvalidCredential -> "El email introducido no es válido. Comprueba que es un email válido"
                FailedSignUp.UserAlreadyExist -> "El usuario que intenta introducir ya está registrado"
                FailedSignUp.WeakPas -> "La contraseña es demasiado debil"
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