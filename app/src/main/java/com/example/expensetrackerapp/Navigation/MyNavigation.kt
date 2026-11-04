package com.example.expensetrackerapp.Navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.savedState
import com.example.expensetrackerapp.Activites.AddExpenseScreen
import com.example.expensetrackerapp.Activites.AnalyticsScreen
import com.example.expensetrackerapp.Activites.ExpenseScreen
import com.example.expensetrackerapp.Activites.HomeActivity
import com.example.expensetrackerapp.Activites.LoginActivity
import com.example.expensetrackerapp.Activites.SettingsScreen
import com.example.expensetrackerapp.Activites.SignUpActivity
import com.example.expensetrackerapp.Activites.splashActivity
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.theme.Primary_blue
import com.example.expensetrackerapp.ui.theme.Pure_white
import com.example.expensetrackerapp.viewmodel.AddExpenseViewmodel
import com.example.expensetrackerapp.viewmodel.HomeViewmodel
import com.example.expensetrackerapp.viewmodel.LoginViewmodel
import com.example.expensetrackerapp.viewmodel.SignUpViewmodel

@Composable
fun MyNavigation()
{
    val navController = rememberNavController()
    val signUpViewmodel: SignUpViewmodel = viewModel()
    val loginViewmodel: LoginViewmodel = viewModel()
    val addExpenseViewmodel: AddExpenseViewmodel = viewModel()
    val homeViewmodel: HomeViewmodel = viewModel()
    val selectedIcon = remember {
        mutableStateOf(R.drawable.home)
    }

    val navCurrentStackBack = navController.currentBackStackEntryAsState()
    val currentRoute = navCurrentStackBack.value?.destination?.route
    Scaffold(
        bottomBar = {
            if (
                currentRoute == "home" ||
                currentRoute == "expense" ||
                currentRoute == "analytics" ||
                currentRoute == "settings"
            ) {
                BottomAppBar(
                    containerColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                ) {

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        IconButton(
                            onClick = {
                                navController.navigate("home")
                                {
                                    popUpTo("home")
                                    {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                               // selectedIcon.value = R.drawable.home
                            },
                            modifier = Modifier.size(100.dp)
                        ) {

                            Column {
                                Icon(
                                    painter = painterResource(R.drawable.home),
                                    contentDescription = null,
                                    tint =
                                        if (currentRoute=="home") {
                                            Primary_blue
                                        } else {
                                            Color.Black
                                        }
                                )

                                Text("Home", fontSize = 12.sp)
                            }
                        }


                    }

                    Column(
                        modifier = Modifier.weight(1f).clickable {
                            navController.navigate("expense")
                            {
                                popUpTo("home")
                                {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }
                           // selectedIcon.value = R.drawable.expense
                        }.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.expense),
                            contentDescription = null,
                            tint =
                                if (currentRoute=="expense") {
                                    Primary_blue
                                } else {
                                    Color.Black
                                }
                        )
                        Text("Expense", fontSize = 12.sp)
                    }

                    Column(
                        modifier = Modifier.weight(1f).padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        FloatingActionButton(
                            onClick = {
                                navController.navigate("addExpense")
                            },
                            shape = CircleShape,
                            containerColor = Primary_blue
                        ) {
                            Icon(imageVector = Icons.Default.Add, null, tint = Pure_white)
                        }

                    }


                    Column(
                        modifier = Modifier.weight(1f)
                        .clickable {
                            navController.navigate("analytics")
                            {
                                popUpTo("home")
                                {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                          //  selectedIcon.value = R.drawable.analytics
                        }
                        .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Icon(
                            painter = painterResource(R.drawable.analytics),
                            contentDescription = null,
                            tint =
                                if (currentRoute=="analytics") {
                                    Primary_blue
                                } else {
                                    Color.Black
                                }
                        )

                        Text("Analytics", fontSize = 12.sp)
                    }


                    Column(
                        modifier = Modifier.weight(1f)
                            .clickable {
                                navController.navigate("settings")
                                {
                                    popUpTo("home")
                                    {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = null,
                            tint =
                                if (currentRoute=="settings") {
                                    Primary_blue
                                } else {
                                    Color.Black
                                }
                        )
                        Text("Settings", fontSize = 12.sp)
                    }
                }
            }
        }
    ) {innerpadding->
        NavHost(navController = navController, startDestination = "splash")
        {
            composable("splash")
            {
                splashActivity{
                    if (loginViewmodel.isUserLoggedIn())
                    {
                        navController.navigate("home")
                        {
                            popUpTo("splash")
                            {
                                inclusive = true
                            }
                        }
                    } else
                    {
                        navController.navigate("SignUp"){
                            popUpTo("splash")
                            {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            composable("SignUp")
            {
                SignUpActivity(navController,signUpViewmodel)
            }
            composable("LogIn")
            {
                LoginActivity(navController,loginViewmodel)
            }
            composable("home")
            {
                HomeActivity(navController,homeViewmodel)
            }
            composable("addExpense")
            {
                AddExpenseScreen(navController,addExpenseViewmodel)
            }
            composable("expense")
            {
                ExpenseScreen()
            }
            composable("analytics")
            {
                AnalyticsScreen(navController,homeViewmodel)
            }
            composable("settings")
            {
                SettingsScreen(navController,homeViewmodel)
            }
        }

    }
}