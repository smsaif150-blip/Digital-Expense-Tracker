package com.example.expensetrackerapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.expensetrackerapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private val _loginState = MutableStateFlow<Resource>(Resource.Test)
    val loginState: StateFlow<Resource> = _loginState

    private val _resetPassState = MutableStateFlow<Resource>(Resource.Test)
    val resetPassState: StateFlow<Resource> = _resetPassState
    private val _resetEmailError = MutableStateFlow<String?>(null)
    val resetEmailError: StateFlow<String?> = _resetEmailError

    fun onEmailChange()
    {
        _emailError.value = null
    }

    fun onPasswordChange()
    {
        _passwordError.value = null
    }

    fun isUserLoggedIn():Boolean
    {
       return auth.currentUser!=null
    }
    fun Login(email: String, password: String)
    {
        if (email.isEmpty())
        {
            _emailError.value = "Email is empty"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _emailError.value = "Wrong email formate"
            return
        }

        if (password.isEmpty())
        {
            _passwordError.value = "Password is empty"
            return
        }
        if (password.length<6)
        {
            _passwordError.value = "password must be 6 character"
            return
        }

        _loginState.value = Resource.Loading

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                _loginState.value = Resource.Success
            }
            .addOnFailureListener {
                _loginState.value = Resource.Failure(it.message.toString())
            }
    }

    fun resetEmailValidation(email: String): Boolean
    {
        if (email.isEmpty())
        {
            _resetEmailError.value = "Please Enter your email to reset your password"
            return true
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _resetEmailError.value = "Wrong email formate"
            return true
        }
        return false
    }

    fun onResetEmailChange()
    {
        _resetEmailError.value = null
    }
    fun resetPassword(email: String)
    {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _resetPassState.value = Resource.Success
            }.addOnFailureListener {
                _resetPassState.value = Resource.Failure(it.message.toString())
            }
    }
}