package com.djcdev.practicas.domain.usecase

import android.util.Log
import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    operator fun invoke(user: String?, pass: String?, logged: (Boolean, FailedLogin?) -> Unit) {

        if (user != null && pass != null) {
            firebaseAuth.signOut()
            firebaseAuth
                .signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        logged(true, null)
                    }
                }
                .addOnFailureListener { exception ->
                    val errorMessage = exception.message
                    Log.e("FirebaseAuth", "Error al logear el usuario: $errorMessage")

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