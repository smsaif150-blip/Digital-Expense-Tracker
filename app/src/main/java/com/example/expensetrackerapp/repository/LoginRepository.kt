package com.example.expensetrackerapp.repository

import com.example.expensetrackerapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginRepository @Inject constructor(private val auth: FirebaseAuth) {


    fun Login(email: String,password: String,onResult:(Resource) -> Unit)
    {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    onResult(Resource.Success)
                }
                else
                {
                    onResult(Resource.Failure(task.exception?.message?:"Login failed"))
                }
            }
    }

    fun resetPassword(email: String,onResult:(Resource)-> Unit)
    {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onResult(Resource.Success)
            }
            .addOnFailureListener {
                onResult(Resource.Failure(it.message.toString()))
            }
    }

    fun userLoggedIn(): Boolean
    {
        return auth.currentUser != null
    }

}