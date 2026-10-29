package com.example.expensetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.repository.HomeRepository
import com.example.expensetrackerapp.utils.HomeState
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(val repository: HomeRepository): ViewModel(){

    private val _homeState = MutableStateFlow<HomeState>(HomeState.HomeLoading)
    val homeState: StateFlow<HomeState> = _homeState

    private val _userName = MutableStateFlow<String>("User")
    val userName: StateFlow<String> = _userName
    private var expenseListener: ListenerRegistration? = null

    init {
        fetchExpense()
        getName()
    }

    fun fetchExpense()
    {
        _homeState.value = HomeState.HomeLoading

        expenseListener?.remove()

        expenseListener = repository.getExpense { result->
            _homeState.value = result
        }

    }


    fun getName()
    {
        repository.getName { name->
            _userName.value = name
        }
    }



    override fun onCleared() {
        expenseListener?.remove()
        expenseListener = null
        super.onCleared()
    }

}