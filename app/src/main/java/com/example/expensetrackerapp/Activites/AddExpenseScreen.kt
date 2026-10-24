package com.example.expensetrackerapp.Activites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.theme.Blue_tined_white
import com.example.expensetrackerapp.ui.theme.Gray_icon
import com.example.expensetrackerapp.ui.theme.Primary_blue
import com.example.expensetrackerapp.ui.theme.Pure_white
import com.example.expensetrackerapp.viewmodel.AddExpenseViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavController)
{
    val viewmodel: AddExpenseViewmodel = viewModel()

    val datePickerState = rememberDatePickerState()
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
                label = {Text("Title(e.g Lunch)", fontSize = 20.sp, color = Gray_icon)},

                )

            OutlinedTextField(value = viewmodel.amount,
                onValueChange = {
                    viewmodel.onAmountChange(it)
                },
                label = {Text("Amount(e.g 50)", fontSize = 20.sp, color = Gray_icon)},

                )

            Spacer(modifier = Modifier.padding(top = 8.dp))

            ExposedDropdownMenuBox(expanded = viewmodel.expanded,
                onExpandedChange = {viewmodel.toggleDropdown()}) {


                OutlinedTextField(value = viewmodel.selectedOptionText,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor(),
                    trailingIcon = {
                       ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewmodel.expanded)
                    })

                ExposedDropdownMenu(expanded = viewmodel.expanded,
                    onDismissRequest = {viewmodel.dismissDropdown()}) {
                    viewmodel.options.forEach { option->
                        DropdownMenuItem(
                            text = {Text(option)},
                            onClick = {
                                viewmodel.selectedOptionText = option
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
                }
            )


            OutlinedTextField(value = viewmodel.note
            , onValueChange = {
                viewmodel.onNoteChange(it)
                },
                modifier = Modifier.height(120.dp).padding(top = 8.dp),
                placeholder = {
                    Text("Note (Optional)", color = Gray_icon, fontSize = 20.sp)
                }
                )

            Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(32.dp),
               colors = ButtonDefaults.buttonColors(Primary_blue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save", fontSize = 20.sp, color = Pure_white)
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun showAddExScreen()
{
    val navController = rememberNavController()
    AddExpenseScreen(navController)
}