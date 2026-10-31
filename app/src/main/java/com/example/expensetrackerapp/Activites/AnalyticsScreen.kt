package com.example.expensetrackerapp.Activites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.expensetrackerapp.ui.theme.Blue_tined_white

@Composable
fun AnalyticsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = Blue_tined_white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Analytics")
    }
}