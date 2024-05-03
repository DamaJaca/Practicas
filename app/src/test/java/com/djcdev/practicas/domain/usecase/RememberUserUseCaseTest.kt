package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.ui.login.exceptions.FailedLogin
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RememberUserUseCaseTest{


    private lateinit var rememberUserUseCase: RememberUserUseCase
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun onBefore(){
        MockitoAnnotations.openMocks(this)
        rememberUserUseCase= RememberUserUseCase(firebaseAuth)
    }


    @Test
    fun `when you try to remember pass and it is succesfull`(){
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedLogin: FailedLogin? = null



        //act
        val task = Mockito.mock(Task::class.java) as Task<Void>

        Mockito.`when`(task.isSuccessful).thenReturn(true)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(firebaseAuth.sendPasswordResetEmail(user)).thenReturn(task)


        rememberUserUseCase(user) { success, failedLogin ->
            capturedSuccess = success
            capturedFailedLogin = failedLogin
        }

        //assert

        assertEquals(true, capturedSuccess)
        assertEquals(null, capturedFailedLogin)
    }

    @Test
    fun `when you try to remember pass and it is completed but not success`(){
        //arrange
        val user = "test@example.com"



        //act
        val task = Mockito.mock(Task::class.java) as Task<Void>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(firebaseAuth.sendPasswordResetEmail(user)).thenReturn(task)


        rememberUserUseCase(user) { _, _ ->
            throw IllegalStateException ( "No se deberia de llamar la funcion" )
        }

        //assert

        Mockito.verify(firebaseAuth).sendPasswordResetEmail(user)
    }

    @Test
    fun `when you try to remember pass and it throws an Invalid User Exception`(){
        //arrange
        val user = "test@example.com"
        var capturedSuccess: Boolean? = null
        var capturedFailedLogin: FailedLogin? = null



        //act
        val task = Mockito.mock(Task::class.java) as Task<Void>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(FirebaseAuthInvalidUserException("error", "error"))
            task
        }
        Mockito.`when`(firebaseAuth.sendPasswordResetEmail(user)).thenReturn(task)


        rememberUserUseCase(user) { success, failedLogin ->
            capturedSuccess = success
            capturedFailedLogin = failedLogin
        }

        //assert

        assertEquals(false, capturedSuccess)
        assertEquals(FailedLogin.InvalidUser, capturedFailedLogin)
    }


}