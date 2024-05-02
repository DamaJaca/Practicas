package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest{

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore() {
        MockitoAnnotations.initMocks(this)
        loginUseCase = LoginUseCase(firebaseAuth)
    }

    @Test
    fun `when you cant loggin for loggedUser`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"


        //act
        val authResult = Mockito.mock(AuthResult::class.java)
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(true)
        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(ArgumentMatchers.eq(user), ArgumentMatchers.eq(pass))).thenReturn(task)

        loginUseCase(user, pass) { success, failedLogin ->
            Assert.assertTrue(success)
            Assert.assertNull(failedLogin)
        }

        //assert

        Mockito.verify(firebaseAuth).signInWithEmailAndPassword(ArgumentMatchers.eq(user), ArgumentMatchers.eq(pass))
    }

}