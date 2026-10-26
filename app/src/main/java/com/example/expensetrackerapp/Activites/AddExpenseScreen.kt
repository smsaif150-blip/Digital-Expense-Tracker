package com.example.expensetrackerapp.Activites

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.theme.Blue_tined_white
import com.example.expensetrackerapp.ui.theme.Gray_icon
import com.example.expensetrackerapp.ui.theme.Primary_blue
import com.example.expensetrackerapp.ui.theme.Pure_white
import com.example.expensetrackerapp.viewmodel.AddExpenseViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavController,viewmodel: AddExpenseViewmodel)
{

    val datePickerState = rememberDatePickerState()

    val titleError by viewmodel.titleError.collectAsState()
    val amountError by viewmodel.amountError.collectAsState()
    val categoryError by viewmodel.categoryError.collectAsState()
    val dateError by viewmodel.dateError.collectAsState()
    val context = LocalContext.current
    val loading = viewmodel.isLoading

    LaunchedEffect(key1 = true) {
        viewmodel.firestoreMessage.collect { message->
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary_blue),
                title = {
                    Text("Add Expense", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Pure_white)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24,),
                            contentDescription ="back button", tint = Pure_white)
                    }
                },
            )
        }
    ) {innerPadding->

        Column(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
            .background(color = Blue_tined_white)
            , verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally) {


            OutlinedTextField(value = viewmodel.title,
                onValueChange = {
                    viewmodel.onTitleChange(it)
                },
                placeholder = {Text("Title(e.g Lunch)", color = Gray_icon)},
                supportingText = {
                    if (titleError!=null)
                    Text(titleError.toString(), color = Color.Red)
                }
                )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            OutlinedTextField(value = viewmodel.amount,
                onValueChange = {
                    viewmodel.onAmountChange(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {Text("Amount(e.g 50)", color = Gray_icon)},
                supportingText = {
                    if (amountError!=null)
                    {
                        Text(amountError.toString(), color = Color.Red)
                    }
                }
                )

            Spacer(modifier = Modifier.padding(top = 8.dp))

            ExposedDropdownMenuBox(expanded = viewmodel.expanded,
                onExpandedChange = {viewmodel.toggleDropdown()}) {

                OutlinedTextField(value = viewmodel.selectedOptionText,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor(),
                    label = {Text("Select category")},
                    trailingIcon = {
                       ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewmodel.expanded)
                    },
                    supportingText = {
                        if (categoryError!=null)
                        {
                            Text(categoryError.toString(), color = Color.Red)
                        }
                    })

                ExposedDropdownMenu(expanded = viewmodel.expanded,
                    onDismissRequest = {viewmodel.dismissDropdown()}) {
                    viewmodel.options.forEach { option->
                        DropdownMenuItem(
                            text = {Text(option)},
                            onClick = {
                                viewmodel.selectedOptionText = option
                                viewmodel.onCategoryChange()
                                viewmodel.expanded = false
                            }
                        )
                    }
                }
            }


            if (viewmodel.showDatePicker)
            {
                DatePickerDialog(onDismissRequest = {viewmodel.showDatePicker = false},
                    confirmButton = {
                        TextButton(onClick = {
                            viewmodel.showDatePicker = false
                            datePickerState.selectedDateMillis?.let { millis->
                                viewmodel.updateDate(millis)
                            }
                        }) {
                            Text("Ok")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            viewmodel.showDatePicker = false
                        }) {
                            Text("Cancel")
                        }
                    }) {
                    DatePicker(state = datePickerState)
                }
            }

            OutlinedTextField(value = viewmodel.selectDateText,
                onValueChange = {},
                readOnly = true,
                label = {Text("Select date")},
                trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date",
                        modifier = Modifier.clickable{
                            viewmodel.showDatePicker = true
                        })
                },
                supportingText = {
                    if (dateError!=null)
                    {
                        Text(dateError.toString(), color = Color.Red)
                    }
                }
            )


            OutlinedTextField(value = viewmodel.note
            , onValueChange = {
                viewmodel.onNoteChange(it)
                },
                modifier = Modifier.height(120.dp).padding(top = 8.dp),
                placeholder = {
                    Text("Note (Optional)", color = Gray_icon,)
                }
                )

            Button(onClick = {
                viewmodel.saveButton(navController)
            }, modifier = Modifier.fillMaxWidth().padding(32.dp),
               colors = ButtonDefaults.buttonColors(Primary_blue),
                shape = RoundedCornerShape(12.dp)
            ) {

                if (loading)
                {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp)
                }else
                {
                    Text("Save", color = Pure_white)
                }


            }
        }
    }
}





//@Preview(showBackground = true)
//@Composable
//fun showAddExScreen()
//{
//    val navController = rememberNavController()
//    AddExpenseScreen(navController)
//}