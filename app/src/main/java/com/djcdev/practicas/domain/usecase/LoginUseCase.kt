package com.djcdev.practicas.domain.usecase

import android.util.Log
import com.djcdev.practicas.ui.login.FailedLogin
import com.djcdev.practicas.ui.login.FailedSignUp
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import javax.inject.Inject

class LoginUseCase @Inject constructor() {

    operator fun invoke(user: String?, pass: String?, logged: (Boolean, FailedLogin?) -> Unit) {

        if (user != null && pass != null) {
            Firebase.auth.signOut()
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        logged(true, null)
                    }
                }
                .addOnFailureListener { exception ->
                    val errorMessage = exception.message
                    Log.e("FirebaseAuth", "Error al crear el usuario: $errorMessage")
                    Log.d("FirebaseAuth", "Error al logear y no se porque")

                    when (exception) {

                        is FirebaseAuthInvalidUserException -> logged(
                            false,
                            FailedLogin.InvalidUser
                        )

                        is FirebaseAuthInvalidCredentialsException -> logged(
                            false,
                            FailedLogin.InvalidPass
                        )

                        is FirebaseAuthUserCollisionException -> logged(
                            false,
                            FailedLogin.LoggedUser
                        )

                        is FirebaseNetworkException -> logged(
                            false,
                            FailedLogin.NetworkFail
                        )

                        is FirebaseTooManyRequestsException -> logged(
                            false,
                            FailedLogin.TooManyRequests
                        )
                        else -> logged(false, null)
                    }
                }
        }
    }

}