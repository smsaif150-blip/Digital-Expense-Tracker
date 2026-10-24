package com.example.expensetrackerapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddExpenseViewmodel: ViewModel() {


    var expanded by mutableStateOf(false)
    val options:List<String> = listOf("Food","Travel","Shopping","Bills","Health","Other")
    var selectedOptionText by mutableStateOf(options[0])


    var title by mutableStateOf("")
    var amount by mutableStateOf("")
    var showDatePicker by mutableStateOf(false)
    var selectDateText by mutableStateOf("")
    var note by mutableStateOf("")



    fun onTitleChange(newText: String)
    {
        title = newText
    }

    fun onAmountChange(newText:String)
    {
        amount = newText
    }

    fun onNoteChange(newText: String)
    {
        note = newText
    }

    fun updateDate(mills: Long)
    {
        selectDateText = convertMillisToDate(mills)
    }

    fun toggleDropdown()
    {
        expanded = !expanded
    }

    fun dismissDropdown()
    {
        expanded = false
    }

    private fun convertMillisToDate(mills:Long): String{
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(mills))
    }


}