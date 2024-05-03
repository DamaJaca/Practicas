package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject

class RememberUserUseCase @Inject constructor(private val firebaseAuth: FirebaseAuth){
    operator fun invoke(user: String, remember: (Boolean, FailedLogin?) -> Unit){
        firebaseAuth
            .sendPasswordResetEmail(user)
            .addOnCompleteListener {
                if (it.isSuccessful){ remember(true, null) }
            }.addOnFailureListener {exception ->

                when (exception) {

                    is FirebaseAuthInvalidUserException -> remember(
                        false,
                        FailedLogin.InvalidUser
                    )

                    is FirebaseNetworkException -> remember(
                        false,
                        FailedLogin.NetworkFail
                    )

                    is FirebaseTooManyRequestsException -> remember(
                        false,
                        FailedLogin.TooManyRequests
                    )
                    else -> remember(false, null)
                }
            }
    }
}