package com.example.expensetrackerapp.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expensetrackerapp.repository.LoginRepository
import com.example.expensetrackerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(private val repo: LoginRepository): ViewModel() {


    var email by mutableStateOf("")
    var password by mutableStateOf("")
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
      // return auth.currentUser!=null
      return  repo.userLoggedIn()
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
        repo.Login(email,password){result ->
            _loginState.value = result
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
        repo.resetPassword(email) {result->
            _resetPassState.value = result
        }
    }
}