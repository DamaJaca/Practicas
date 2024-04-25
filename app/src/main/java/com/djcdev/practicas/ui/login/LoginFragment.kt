package com.djcdev.practicas.ui.login

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.djcdev.practicas.R
import com.djcdev.practicas.databinding.FragmentLoginBinding
import com.djcdev.practicas.ui.facturas.filter.dataStore
import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    val binding get() = _binding!!

    private val viewModel by activityViewModels<LoginViewModel>()
    var condition :Boolean =true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initListeners()
    }

    private fun initUI() {


        binding.principalLayout.isVisible=true
        binding.pbRememberMyUser.isVisible=false


        CoroutineScope(Dispatchers.IO).launch {
            getUser().filter { condition }.collect {
                if (it.user != "" && it.pass!="" && it.user!=null) {
                    requireActivity().runOnUiThread {
                        binding.principalLayout.isVisible=false
                        binding.pbRememberMyUser.isVisible=true
                        binding.etUser.setText(it.user)
                    }
                    viewModel.login(it.user, it.pass!!) { boolean, fail ->
                        loginController(boolean, fail)
                    }

                }
                condition=false
            }
            if (!condition){
                saveUser("","")
            }
        }
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            val user = binding.etUser.text.toString()
            val pass = binding.etPass.text.toString()
            if (user != "" && pass != "") {
                binding.pbLogin.isVisible = true
                binding.btnLogin.text = ""
                viewModel.login(user, pass) { boolean, fail -> loginController(boolean, fail) }
            } else {
                showErrorDialog(FailedLogin.MissingSomething)
            }
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSingupFragment()
            )
        }
        binding.tvForgottenInfo.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            )
        }
        binding.ivShowPass.setOnClickListener {
            if (binding.etPass.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.etPass.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.etPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            binding.etPass.setSelection(binding.etPass.text.length)
        }

    }

    private fun loginController(boolean: Boolean, fail: FailedLogin?) {
        if (fail == null) {
            if (boolean) {
                Toast.makeText(context, "Se ha iniciado sesion", Toast.LENGTH_SHORT).show()
                binding.pbLogin.isVisible = false
                binding.btnLogin.text = getString(R.string.login)

                if (binding.cbRememberPass.isChecked){
                    CoroutineScope(Dispatchers.IO).launch {
                        saveUser (binding.etUser.text.toString(), binding.etPass.text.toString())
                    }
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        saveUser ("", "")
                    }
                }



                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                )
            }
        } else {
            binding.pbLogin.isVisible = false
            binding.btnLogin.text = getString(R.string.login)
            showErrorDialog(fail)
        }

    }

    private suspend fun saveUser(user: String, pass: String) {
        requireContext().dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user")] = user
            preferences[stringPreferencesKey("pass")] = pass

        }
    }

    private fun showErrorDialog(fail: FailedLogin?) {
        var errorMessage = ""
        errorMessage = if (fail == null) {
            "Ha ocurrido un error inesperado al intentar realizar esa acción"
        } else {
            when (fail) {
                FailedLogin.InvalidUser -> "El usuario que está intentando ingresar no existe"
                FailedLogin.InvalidPass -> "Usuario o Contraseña no válida"
                FailedLogin.LoggedUser -> "Error al logear usuario. Compruebe que no inició sesion en otro dispositivo"
                FailedLogin.MissingSomething -> "Usuario o contraseña faltante"
                FailedLogin.NetworkFail -> "No se ha podido conectar con el servidor"
                FailedLogin.TooManyRequests -> "Ha agotado todos los intentos de inicio de sesion. Intentelo más adelante"
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

    private fun getUser(): Flow<UserModel> {
        return requireContext().dataStore.data.map {
            UserModel(
                it[stringPreferencesKey("user")],
                it[stringPreferencesKey("pass")]
            )
        }
    }


}
