package com.example.expensetrackerapp.utils

sealed class Resource {
    object Test: Resource()
    object Success: Resource()
    object Loading: Resource()
    data class Failure(val message: String): Resource()
}