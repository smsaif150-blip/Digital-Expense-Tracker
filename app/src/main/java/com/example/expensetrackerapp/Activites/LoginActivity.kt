package com.example.expensetrackerapp.Activites

import android.R
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.ui.theme.Blue
import com.example.expensetrackerapp.ui.theme.Light_gray
import com.example.expensetrackerapp.viewmodel.LoginViewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.expensetrackerapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun LoginActivity(navController: NavController,loginViewmodel: LoginViewmodel)
{

    val emailError by loginViewmodel.emailError.collectAsState()
    val passwordError by loginViewmodel.passwordError.collectAsState()
    val loginState by loginViewmodel.loginState.collectAsState()
    val resetPassState by loginViewmodel.resetPassState.collectAsState()
    val resetEmailError by loginViewmodel.resetEmailError.collectAsState()
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var showResetPasswordDialog by remember {
        mutableStateOf(false)
    }

    var resetEmail by remember {
        mutableStateOf("")
    }
    LaunchedEffect(resetPassState) {
        when(resetPassState)
        {
            is Resource.Success -> {
                Toast.makeText(context,"go to your mail inbox or spam box", Toast.LENGTH_SHORT).show()
            }
            is Resource.Failure -> {
                Toast.makeText(context,(resetPassState as Resource.Failure).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }
    LaunchedEffect(loginState) {
        when(loginState)
        {
            is Resource.Loading -> isLoading = true
            is Resource.Success -> {
                isLoading = false
                Toast.makeText(context,"Successfully Log in", Toast.LENGTH_SHORT).show()
                navController.navigate("home")
                {
                    popUpTo("LogIn")
                    {
                        inclusive = true
                    }
                }
            }
            is Resource.Failure -> {
                isLoading = false
                Toast.makeText(context,"Login Unsuccessful",Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Blue)){

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Login Account", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp)
            .background(
                color = Color.White,
                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ))
        {
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                OutlinedTextField(value =email,
                    onValueChange = {
                        email = it
                        loginViewmodel.onEmailChange()
                    },
                    modifier = Modifier.padding(top = 72.dp),
                    label = {
                        Text("Email", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Light_gray,
                        unfocusedBorderColor = Light_gray
                    ),
                    supportingText = {
                        if (emailError!=null)
                        Text(emailError.toString(), color = MaterialTheme.colorScheme.error)
                    }
                )

                OutlinedTextField(value =password, onValueChange = {
                    password = it
                    loginViewmodel.onPasswordChange()
                },
                    modifier = Modifier.padding(top = 12.dp),
                    label = {
                        Text("Password", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Email")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Light_gray,
                        unfocusedBorderColor = Light_gray
                    ),
                    supportingText = {
                        if (passwordError != null)
                        {
                            Text(passwordError.toString(), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )


                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = {
                    showResetPasswordDialog = true
                }, modifier = Modifier.padding(start = 120.dp)) {
                    Text("Forgot Password?", fontSize = 14.sp, color = Blue, fontWeight = FontWeight.Bold)
                }





                Button(onClick = {
                    loginViewmodel.Login(email.trim(),password.trim())
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(start = 50.dp, end = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        Blue
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isLoading

                ) {
                    if (isLoading)
                    {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else
                    {
                        Text("Log In", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Don't have any account?")
                TextButton(onClick = {
                    navController.navigate("SignUp")
                    {
                        popUpTo("LogIn")
                        {
                            inclusive = true
                        }
                    }
                }) {
                    Text("Sign Up", color =  Blue,fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

            }
        }
    }

    if (showResetPasswordDialog)
    {
        AlertDialog(
            onDismissRequest = {showResetPasswordDialog = false},
            title = {Text(text = "Want to reset password?")},
            text = {
                OutlinedTextField(value = resetEmail,
                    onValueChange = {resetEmail = it
                                    loginViewmodel.onResetEmailChange()},
                    label = {Text("Enter email for reset password")},
                    supportingText = {
                        if (resetEmailError != null) {
                            Text(
                                text = resetEmailError.toString(),
                                color = Color.Red
                            )
                        }
                    }
                    )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (loginViewmodel.resetEmailValidation(resetEmail))
                    {
                        return@TextButton
                    }
                    loginViewmodel.resetPassword(resetEmail.trim())
                    showResetPasswordDialog = false
                    resetEmail = ""
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {showResetPasswordDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun showLoginActivity()
//{
//    ExpenseTrackerAppTheme {
//        LoginActivity()
//    }
//}