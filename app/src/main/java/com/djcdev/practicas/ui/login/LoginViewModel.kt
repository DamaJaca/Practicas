package com.djcdev.practicas.ui.login

import androidx.lifecycle.ViewModel
import com.djcdev.practicas.domain.usecase.LoginUseCase
import com.djcdev.practicas.domain.usecase.RememberUserUseCase
import com.djcdev.practicas.domain.usecase.SingUpUseCase
import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import com.djcdev.practicas.ui.login.exceptions.FailedSignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val singUpUseCase: SingUpUseCase,
    private val loginUseCase: LoginUseCase,
    private val rememberUserUseCase: RememberUserUseCase
) : ViewModel() {
    fun singUp(user: String, pass: String, onComplete: (Boolean, FailedSignUp?) -> Unit) {
        singUpUseCase.invoke(user, pass) { bolean, fail -> onComplete(bolean, fail) }
    }

    fun login(user: String, pass: String, logged: (Boolean, FailedLogin?) -> Unit) {
        loginUseCase.invoke(user, pass) { boolean, failed -> logged(boolean, failed) }
    }

    fun remember(user: String, remember: (Boolean, FailedLogin?) -> Unit) {
        rememberUserUseCase(user) { boolean, failed -> remember(boolean, failed) }
    }
}

