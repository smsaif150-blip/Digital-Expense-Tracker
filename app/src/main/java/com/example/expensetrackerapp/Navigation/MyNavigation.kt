package com.example.expensetrackerapp.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerapp.Activites.AddExpenseScreen
import com.example.expensetrackerapp.Activites.AnalyticsScreen
import com.example.expensetrackerapp.Activites.ExpenseScreen
import com.example.expensetrackerapp.Activites.HomeActivity
import com.example.expensetrackerapp.Activites.LoginActivity
import com.example.expensetrackerapp.Activites.SignUpActivity
import com.example.expensetrackerapp.Activites.splashActivity
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
            AnalyticsScreen()
        }
    }
}