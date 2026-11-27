package com.example.expensetrackerapp.Activites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.expensetrackerapp.ui.theme.Blue_tined_white
import com.example.expensetrackerapp.viewmodel.HomeViewmodel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsScreen(navController: NavController,homeViewmodel: HomeViewmodel) {
    Column(modifier = Modifier.fillMaxSize().background(color = Blue_tined_white), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Setting")

        val auth = FirebaseAuth.getInstance()
        TextButton(
            {auth.signOut()
            navController.navigate("SignUp")
            {
                popUpTo("settings"){
                    inclusive = true
                }
            }

            }

        ) {
            Text("Sign Out")
        }
    }
}