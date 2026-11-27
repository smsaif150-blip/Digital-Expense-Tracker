package com.example.expensetrackerapp.Activites

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.model.User
import com.example.expensetrackerapp.ui.theme.Blue
import com.example.expensetrackerapp.ui.theme.Border_Color
import com.example.expensetrackerapp.ui.theme.Deep_blue
import com.example.expensetrackerapp.ui.theme.Focused_Border
import com.example.expensetrackerapp.ui.theme.Input_Text
import com.example.expensetrackerapp.ui.theme.Light_gray
import com.example.expensetrackerapp.ui.theme.TextField_Background
import com.example.expensetrackerapp.utils.Resource
import com.example.expensetrackerapp.viewmodel.SignUpViewmodel

@Composable
fun SignUpActivity(navController: NavController,signUpViewmodel: SignUpViewmodel)
{
    val signupState by signUpViewmodel.signupState.collectAsState()
    val nameError by signUpViewmodel.nameError.collectAsState()
    val emailError by signUpViewmodel.emailError.collectAsState()
    val passwordError by signUpViewmodel.passwordError.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(signupState) {
        when(signupState)
        {
            is Resource.Loading -> isLoading = true
            is Resource.Success -> {
                isLoading = false
                navController.navigate("LogIn"){
                    popUpTo("SignUp"){
                        inclusive = true
                    }
                }
                Toast.makeText(context,"Sign up successful", Toast.LENGTH_SHORT).show()
                signUpViewmodel.resetSignupState()
            }
            is Resource.Failure -> {
                isLoading = false
                Toast.makeText(context,(signupState as Resource.Failure).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }
    val user = User(signUpViewmodel.name,signUpViewmodel.email)


    Box(modifier = Modifier.fillMaxSize()){

//        Column(modifier = Modifier.fillMaxWidth().padding(top = 120.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("Create Account", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
//        }

        Image(painter = painterResource(R.drawable.signup),null)

      Box(modifier = Modifier.fillMaxSize().padding(top = 300.dp))
      {
          Column(modifier = Modifier.fillMaxWidth(),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally) {

              OutlinedTextField(value =signUpViewmodel.name, onValueChange =
                  {
                      signUpViewmodel.name = it
                      signUpViewmodel.onNameChange()
                  },
                  modifier = Modifier.padding(top = 72.dp),
                  placeholder = {
                      Text("Name",  fontSize = 16.sp, color = Input_Text)
                  },
                  leadingIcon = {
                      Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Name", tint = Blue)
                  },
                  shape = RoundedCornerShape(16.dp),
                  colors = OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = Focused_Border,
                      unfocusedBorderColor = Border_Color,
                      focusedContainerColor = TextField_Background,
                      unfocusedContainerColor = TextField_Background
                  ),
                  supportingText = {
                      if (nameError != null)
                      {
                          Text(nameError.toString(), color = MaterialTheme.colorScheme.error)
                      }
                  }
                  )

              OutlinedTextField(value = signUpViewmodel.email, onValueChange = {
                  signUpViewmodel.email = it
                  signUpViewmodel.onEmailChange()
              },
                  placeholder = {
                      Text("Email", fontSize = 16.sp, color = Input_Text)
                  },
                  leadingIcon = {
                      Icon(imageVector = Icons.Default.Email, contentDescription = "Email", tint = Blue)
                  },
                  shape = RoundedCornerShape(16.dp),
                  colors = OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = Focused_Border,
                      unfocusedBorderColor = Border_Color,
                      focusedContainerColor = TextField_Background,
                      unfocusedContainerColor = TextField_Background
                  ),
                  supportingText = {
                      if(emailError!=null)
                      {
                          Text(emailError.toString(), color = MaterialTheme.colorScheme.error)
                      }
                  }
              )

              OutlinedTextField(value = signUpViewmodel.password, onValueChange =
                  {
                  signUpViewmodel.password = it
                  signUpViewmodel.onPasswordChange()
                  },
                 // modifier = Modifier.padding(top = 20.dp),
                  placeholder = {
                      Text("Password",  fontSize = 16.sp, color = Input_Text)
                  },
                  leadingIcon = {
                      Icon(imageVector = Icons.Default.Lock, contentDescription = "password", tint = Blue)
                  },
                  shape = RoundedCornerShape(16.dp),
                  colors = OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = Focused_Border,
                      unfocusedBorderColor = Border_Color,
                      focusedContainerColor = TextField_Background,
                      unfocusedContainerColor = TextField_Background
                  ),
                  supportingText = {
                      if (passwordError!=null)
                      {
                         Text(passwordError.toString(),color = MaterialTheme.colorScheme.error)
                      }
                  }
                  )

              Spacer(modifier = Modifier.padding(bottom = 8.dp))
              Button(onClick = {
                      signUpViewmodel.SignUp(user,signUpViewmodel.password)
              },
                  modifier = Modifier.fillMaxWidth().height(48.dp).padding(start = 50.dp, end = 50.dp),
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
                  }
                  else{
                      Text("Sign Up", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                  }
              }
              Spacer(modifier = Modifier.padding(top = 12.dp))
              Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                  Text("Already have an account?", fontSize = 16.sp)
                  TextButton(onClick = {
                      navController.navigate("LogIn")
                      {
                          popUpTo("SignUp")
                          {
                              inclusive = true
                          }
                      }
                  }) {
                      Text("Login", fontSize = 16.sp, color = Deep_blue, fontWeight = FontWeight.Bold)
                  }
              }

          }
      }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun showSignup()
//{
//    ExpenseTrackerAppTheme {
//        SignUpActivity()
//    }
//}