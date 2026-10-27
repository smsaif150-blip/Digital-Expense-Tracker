package com.example.expensetrackerapp.model


data class Expenses(
    var id: String = "",
    var title: String = "",
    var amount: String = "",
    var category: String = "",
    var date: String = "",
    var note: String = "",
    var userId: String = ""
)
