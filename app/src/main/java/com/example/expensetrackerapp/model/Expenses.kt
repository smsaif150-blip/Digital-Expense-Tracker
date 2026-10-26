package com.example.expensetrackerapp.model


data class Expenses(
    val id: String = "",
    val title: String,
    val amount: String,
    val category: String,
    val date: String,
    val note: String = "",
    val userId: String
)
