package com.djcdev.practicas.ui.login

import androidx.lifecycle.ViewModel
import com.djcdev.practicas.domain.usecase.SingUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val singUpUseCase: SingUpUseCase) :ViewModel(){
    fun singUp (user:String, pass:String, onComplete: (Boolean, FailedSignUp?) -> Unit) {
        singUpUseCase.invoke(user, pass){bolean, fail -> onComplete(bolean, fail)}
    }
}

