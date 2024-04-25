package com.djcdev.practicas.ui.login.exceptions

sealed class FailedLogin {

    data object InvalidUser: FailedLogin()
    data object InvalidPass: FailedLogin()
    data object LoggedUser: FailedLogin()
    data object NetworkFail: FailedLogin()
    data object TooManyRequests: FailedLogin()
    data object MissingSomething: FailedLogin()
}