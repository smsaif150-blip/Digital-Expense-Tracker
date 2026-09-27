package com.example.expensetrackerapp.Activites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.theme.ExpenseTrackerAppTheme
import kotlinx.coroutines.delay

@Composable
fun splashActivity(onNavHost:()-> Unit)
{

    LaunchedEffect(true) {
        delay(2000L)
        onNavHost()
    }

    Column(modifier = Modifier.fillMaxSize().padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.expenses),
            contentDescription = null, modifier = Modifier.size(200.dp))

        Text("Expense", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.Black
        , modifier = Modifier.padding(top = 12.dp))
        Text("Tracker", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Text("Track Your Money", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp))
        Text("Better", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))
        CircularProgressIndicator(
            color = Color.Black,
            trackColor = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun showSplashScreen()
{
    ExpenseTrackerAppTheme {
        splashActivity {  }
    }
}