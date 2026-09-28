package com.example.expensetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.expensetrackerapp.model.User
import com.example.expensetrackerapp.utils.EmailValidationCheck
import com.example.expensetrackerapp.utils.PasswordValidationCheck
import com.example.expensetrackerapp.utils.SignUpValidation

class SignUpViewmodel: ViewModel(){

    fun createUserWithEmailAndPassword(user: User,password: String)
    {
        if (checkValidation(user.email,password))
        {

        }
    }

    fun checkValidation(email: String,password: String): Boolean
    {
        val emailValid = EmailValidationCheck(email)
        val passwordValid = PasswordValidationCheck(password)
        val shouldSignUp = emailValid is SignUpValidation.Success && passwordValid is SignUpValidation.Success
        return shouldSignUp
    }

}