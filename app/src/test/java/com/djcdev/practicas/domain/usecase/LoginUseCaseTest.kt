package com.djcdev.practicas.domain.usecase

import android.text.TextUtils
import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.w3c.dom.Text
import java.lang.IllegalStateException

@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest{

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginUseCase(firebaseAuth)
    }

    @Test
    fun `when you Login succesfully`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedLogin: FailedLogin? = null



        //act
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(task.isSuccessful).thenReturn(true)
        `when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        `when`(firebaseAuth.signInWithEmailAndPassword(user, pass)).thenReturn(task)


        loginUseCase(user, pass) { success, failedLogin ->
            capturedSuccess = success
            capturedFailedLogin = failedLogin
        }

        //assert

        assertEquals(true, capturedSuccess)
        assertEquals(null, capturedFailedLogin)
    }

    @Test
    fun `when you failed login so you get an exepction(InvalidUser)`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedLogin: FailedLogin? = null




        //act
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        `when`(task.addOnFailureListener (ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(FirebaseAuthInvalidUserException("error", "error"))
            task
        }
        `when`(firebaseAuth.signInWithEmailAndPassword(user, pass)).thenReturn(task)


        loginUseCase(user,  pass) { success, failedLogin ->
            capturedSuccess = success
            capturedFailedLogin = failedLogin
        }

        //assert

        assertEquals(false, capturedSuccess)
        assertEquals(FailedLogin.InvalidUser, capturedFailedLogin)
    }
    @Test
    fun `when you complete the log but its not success so it doesnt complete`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"



        //act
        val task = mock(Task::class.java) as Task<AuthResult>

        `when`(task.isSuccessful).thenReturn(false)
        `when`(task.addOnCompleteListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        `when`(firebaseAuth.signInWithEmailAndPassword(user, pass)).thenReturn(task)


        loginUseCase(user, pass){_,_->
            throw IllegalStateException ( "No se deberia de llamar la funcion" )
        }
        //assert

        verify(firebaseAuth).signInWithEmailAndPassword(user, pass)
    }

}