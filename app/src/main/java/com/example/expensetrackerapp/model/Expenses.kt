package com.example.expensetrackerapp.model

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

data class Expenses(
    val id: String = "",
    val title: String,
    val amount: String,
    val category: String,
    val date: Timestamp,
    val note: String = "",
    val userId: String
)
