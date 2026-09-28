package com.example.expensetrackerapp.utils

sealed class SignUpValidation {
    object Success: SignUpValidation()
    data class Failure(val message: String): SignUpValidation()
}