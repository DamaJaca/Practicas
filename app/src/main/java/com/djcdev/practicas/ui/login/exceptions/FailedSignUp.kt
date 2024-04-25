package com.djcdev.practicas.ui.login.exceptions

sealed class FailedSignUp {
    data object WeakPas: FailedSignUp()
    data object InvalidCredential: FailedSignUp()
    data object UserAlreadyExist: FailedSignUp()
    data object NotSamePass: FailedSignUp()

}