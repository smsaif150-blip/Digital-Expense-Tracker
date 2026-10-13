package com.example.expensetrackerapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.expensetrackerapp.model.User
import com.example.expensetrackerapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewmodel @Inject constructor(private val auth: FirebaseAuth,
                                           private val db: FirebaseFirestore ) : ViewModel(){
    private val _signupState = MutableStateFlow<Resource>(Resource.Test)
    val signupState: StateFlow<Resource> = _signupState
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError
    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError
    private val _nameError = MutableStateFlow<String?>(null)
    val nameError : StateFlow<String?> = _nameError

    fun onEmailChange()
    {
        _emailError.value = null
    }
    fun onPasswordChange()
    {
        _passwordError.value = null
    }

    fun onNameChange()
    {
        _nameError.value = null
    }

    fun SignUp(user: User,password: String)
    {
        val email = user.email.trim()
        val name = user.name.trim()

        if (name.isEmpty())
        {
            _nameError.value = "Please Enter your name"
            return
        }

        if (email.isEmpty())
        {
            _emailError.value = "Please Enter your email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _emailError.value = "Please Enter a valid email"
            return
        }
        if (password.isEmpty())
        {
            _passwordError.value = "Please Enter your password"
            return
        }

        if (password.length< 6)
        {
            _passwordError.value = "password must be greater than 6 character"
            return
        }

        _signupState.value = Resource.Loading

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    task.result.user?.let { firebaseUser ->
                        saveUser(firebaseUser.uid,user){isSaved ->

                            if (!isSaved)
                            {
                                firebaseUser.delete().addOnCompleteListener { deleteTask->
                                    if (deleteTask.isSuccessful)
                                    {
                                       _signupState.value =  Resource.Failure("Authentication failed")
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    _emailError.value = task.exception?.message.toString()
                    _signupState.value = Resource.Failure(_emailError.value.toString())
                }

            }

    }



   private fun saveUser(uId: String,user: User,isSaved:(Boolean) -> Unit)
    {
        db.collection("users")
            .document(uId)
            .set(user)
            .addOnSuccessListener {
                _signupState.value = Resource.Success
                isSaved(true)
            }
            .addOnFailureListener {
                isSaved(false)
               _signupState.value = Resource.Failure(it.message.toString())
            }
    }
}