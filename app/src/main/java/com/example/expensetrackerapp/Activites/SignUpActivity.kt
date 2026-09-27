package com.example.expensetrackerapp.Activites

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetrackerapp.ui.theme.ExpenseTrackerAppTheme

@Composable
fun SignUpActivity()
{
    Text("Sign up Activity")
}


@Preview(showBackground = true)
@Composable
fun showSignup()
{
    ExpenseTrackerAppTheme {
        SignUpActivity()
    }
}