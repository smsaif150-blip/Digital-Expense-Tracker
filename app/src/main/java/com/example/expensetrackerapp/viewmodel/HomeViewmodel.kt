package com.example.expensetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.repository.HomeRepository
import com.example.expensetrackerapp.utils.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(val repository: HomeRepository): ViewModel(){

    private val _homeState = MutableStateFlow<HomeState>(HomeState.HomeLoading)
    val homeState: StateFlow<HomeState> = _homeState

    init {
        fetchExpense()
    }

    fun fetchExpense()
    {
        viewModelScope.launch {
            _homeState.value = HomeState.HomeLoading

            val result = repository.getExpense()
            _homeState.value = result
        }
    }

}