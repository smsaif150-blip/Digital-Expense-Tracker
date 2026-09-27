package com.example.expensetrackerapp.Activites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerapp.ui.theme.Blue
import com.example.expensetrackerapp.ui.theme.ExpenseTrackerAppTheme
import com.example.expensetrackerapp.ui.theme.Light_gray

@Composable
fun LoginActivity()
{
    Box(modifier = Modifier.fillMaxSize().background(color = Blue)){

        Column(modifier = Modifier.fillMaxWidth().padding(top = 120.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Create Account", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Box(modifier = Modifier.fillMaxSize().padding(top = 200.dp).background(color = Color.White,
            RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ))
        {
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                OutlinedTextField(value ="", onValueChange = {},
                    modifier = Modifier.padding(top = 72.dp),
                    placeholder = {
                        Text("Email", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Light_gray,
                        unfocusedBorderColor = Light_gray
                    )
                )

                OutlinedTextField(value ="", onValueChange = {},
                    modifier = Modifier.padding(top = 20.dp),
                    placeholder = {
                        Text("Password", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Email")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Light_gray,
                        unfocusedBorderColor = Light_gray
                    )
                )


                Spacer(modifier = Modifier.padding(20.dp))

                Button(onClick = {},
                    modifier = Modifier.fillMaxWidth().height(48.dp).padding(start = 50.dp, end = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        Blue
                    ),
                    shape = RoundedCornerShape(12.dp)

                ) {
                    Text("Log In", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Forgot Password?", fontSize = 24.sp, color = Blue, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun showLoginActivity()
{
    ExpenseTrackerAppTheme {
        LoginActivity()
    }
}