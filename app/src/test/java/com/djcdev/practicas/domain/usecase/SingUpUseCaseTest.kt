package com.djcdev.practicas.domain.usecase

import com.djcdev.practicas.ui.login.exceptions.FailedSignUp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class SingUpUseCaseTest{
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var signupUseCase: SingUpUseCase

    @Before
    fun onBefore() {
        MockitoAnnotations.openMocks(this)
        signupUseCase = SingUpUseCase(firebaseAuth)
    }

    @Test
    fun `when you SignUp succesfully`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedLogin: FailedSignUp? = null



        //act
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(true)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(user, pass)).thenReturn(task)


        signupUseCase(user, pass) { success, failedSign ->
            capturedSuccess = success
            capturedFailedLogin = failedSign
        }

        //assert

        assertEquals(true, capturedSuccess)
        assertEquals(null, capturedFailedLogin)
    }

    @Test
    fun `when you SignUp but its not success`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedLogin: FailedSignUp? = null



        //act
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(user, pass)).thenReturn(task)


        signupUseCase(user, pass) { success, failedSign ->
            throw IllegalStateException ( "No se deberia de llamar la funcion" )
        }

        //assert

        Mockito.verify(firebaseAuth).createUserWithEmailAndPassword(user, pass)
    }

    @Test
    fun `when you try to signUp but it throws an FirebaseAuthWeakPasswordException`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedSign: FailedSignUp? = null


        //act
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(FirebaseAuthWeakPasswordException("error", "error","error"))
            task
        }
        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(user, pass)).thenReturn(task)


        signupUseCase(user, pass) { success, failedSign ->
            capturedSuccess = success
            capturedFailedSign = failedSign
        }

        //assert

        assertEquals(false, capturedSuccess)
        assertEquals(FailedSignUp.WeakPas, capturedFailedSign)
    }

    @Test
    fun `when you try to signUp but it throws an FirebaseAuthInvalidCredentialsException`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedSign: FailedSignUp? = null


        //act
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(FirebaseAuthInvalidCredentialsException("error", "error"))
            task
        }
        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(user, pass)).thenReturn(task)


        signupUseCase(user, pass) { success, failedSign ->
            capturedSuccess = success
            capturedFailedSign = failedSign
        }

        //assert

        assertEquals(false, capturedSuccess)
        assertEquals(FailedSignUp.InvalidCredential, capturedFailedSign)
    }

    @Test
    fun `when you try to signUp but it throws an FirebaseAuthUserCollisionException`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedSign: FailedSignUp? = null


        //act
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(FirebaseAuthUserCollisionException("error", "error"))
            task
        }
        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(user, pass)).thenReturn(task)


        signupUseCase(user, pass) { success, failedSign ->
            capturedSuccess = success
            capturedFailedSign = failedSign
        }

        //assert

        assertEquals(false, capturedSuccess)
        assertEquals(FailedSignUp.UserAlreadyExist, capturedFailedSign)
    }

    @Test
    fun `when you try to signUp but it throws an unkown Exception`() {
        //arrange
        val user = "test@example.com"
        val pass = "password"
        var capturedSuccess: Boolean? = null
        var capturedFailedSign: FailedSignUp? = null


        //act
        val task = Mockito.mock(Task::class.java) as Task<AuthResult>

        Mockito.`when`(task.isSuccessful).thenReturn(false)
        Mockito.`when`(task.addOnCompleteListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnCompleteListener<AuthResult>
            listener.onComplete(task)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnFailureListener
            listener.onFailure(Exception("error"))
            task
        }
        Mockito.`when`(firebaseAuth.createUserWithEmailAndPassword(user, pass)).thenReturn(task)


        signupUseCase(user, pass) { success, failedSign ->
            capturedSuccess = success
            capturedFailedSign = failedSign
        }

        //assert

        assertEquals(false, capturedSuccess)
        assertEquals(null, capturedFailedSign)
    }
}