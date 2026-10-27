package com.example.expensetrackerapp.utils

import com.example.expensetrackerapp.model.Expenses

sealed class HomeState {
    data class HomeSuccess(val data:List<Expenses>): HomeState()
    data class HomeFailure(val message: String): HomeState()
    object HomeLoading: HomeState()
}