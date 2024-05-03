package com.djcdev.practicas.domain.usecase

import android.util.Log
import com.djcdev.practicas.ui.login.exceptions.FailedSignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import javax.inject.Inject

class SingUpUseCase @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    operator fun invoke(user: String?, pass: String?, onComplete: (Boolean, FailedSignUp?) -> Unit) {
        if (user != null && pass != null) {
            firebaseAuth
                .createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete(true, null)
                    }
                }
                .addOnFailureListener { exception ->
                    val errorMessage = exception.message
                    Log.e("FirebaseAuth", "Error al crear el usuario: $errorMessage")

                    when (exception) {

                        is FirebaseAuthWeakPasswordException -> onComplete(
                            false,
                            FailedSignUp.WeakPas
                        )

                        is FirebaseAuthInvalidCredentialsException -> onComplete(
                            false,
                            FailedSignUp.InvalidCredential
                        )

                        is FirebaseAuthUserCollisionException -> onComplete(
                            false,
                            FailedSignUp.UserAlreadyExist
                        )

                        else -> onComplete(false, null)
                    }
                }
        }
    }

}