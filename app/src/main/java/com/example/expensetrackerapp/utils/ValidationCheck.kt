package com.example.expensetrackerapp.utils

import android.util.Patterns

fun EmailValidationCheck(email: String): SignUpValidation
{
    if (email.isEmpty())
    {
        return SignUpValidation.Failure("Please Enter your Email")
    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
    {
        return SignUpValidation.Failure("Please Enter a valid Email")
    }
    return SignUpValidation.Success
}

fun PasswordValidationCheck(password: String): SignUpValidation
{
    if (password.isEmpty())
    {
        return SignUpValidation.Failure("Please enter your password")
    }
    else if(password.length<6)
    {
        return SignUpValidation.Failure("Password must be contain 6 character")
    }
    return SignUpValidation.Success
}