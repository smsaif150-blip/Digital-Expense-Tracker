package com.example.expensetrackerapp.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerapp.model.Expenses
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewmodel @Inject constructor(private val auth: FirebaseAuth,
                                             private val db: FirebaseFirestore): ViewModel() {
    var isLoading by mutableStateOf(false)
    var expanded by mutableStateOf(false)
    val options:List<String> = listOf("Food","Travel","Shopping","Bills","Health","Other")



    var title by mutableStateOf("")
    var amount by mutableStateOf("")
    var showDatePicker by mutableStateOf(false)
    var selectedOptionText by mutableStateOf("")
    var selectDateText by mutableStateOf("")
    var note by mutableStateOf("")

    private var _titleError = MutableStateFlow<String?>(null)
    var titleError: StateFlow<String?> = _titleError

   private  var _amountError = MutableStateFlow<String?>(null)
    var amountError: StateFlow<String?> = _amountError

  private  var _categoryError = MutableStateFlow<String?>(null)
    var categoryError: StateFlow<String?> = _categoryError

   private var _dateError = MutableStateFlow<String?>(null)
    var dateError: StateFlow<String?> = _dateError

    val id = UUID.randomUUID().toString()




    private var _firestoreMessage = MutableSharedFlow<String>()
    var firestoreMessage: SharedFlow<String> = _firestoreMessage



    fun saveButton(navController: NavController)
    {
        val userId = auth.currentUser?.uid.toString()

        if (title.isEmpty())
        {
            _titleError.value = "Please Enter title"
            return
        }

        if (amount.isEmpty())
        {
            _amountError.value = "Please Enter amount"
            return
        }

        if (selectedOptionText.isEmpty())
        {
            _categoryError.value = "Please select a category"
            return
        }

        if (selectDateText.isEmpty())
        {
            _dateError.value = "Please select date"
            return
        }


        isLoading = true
        val expense = Expenses(id,title,amount,selectedOptionText,selectDateText,note,userId)

        db.collection("Expenses")
            .document(expense.id)
            .set(expense)
            .addOnSuccessListener {
                isLoading = false
                viewModelScope.launch {
                    _firestoreMessage.emit("Data successfully saved")
                }

                title = ""
                selectedOptionText = ""
                amount = ""
                selectDateText = ""
                note = ""

                navController.popBackStack()
            }
            .addOnFailureListener {
                isLoading = false
                viewModelScope.launch {
                    _firestoreMessage.emit(it.message.toString())
                }
            }
    }

    fun onTitleChange(newText: String)
    {
        title = newText
        _titleError.value = null
    }

    fun onAmountChange(newText:String)
    {
        if (newText.all { it.isDigit() }){
            amount = newText
            _amountError.value = null
        }

    }

    fun onCategoryChange()
    {
        _categoryError.value = null
    }

    fun onNoteChange(newText: String)
    {
        note = newText
    }

    fun updateDate(mills: Long)
    {
        selectDateText = convertMillisToDate(mills)
        _dateError.value = null
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