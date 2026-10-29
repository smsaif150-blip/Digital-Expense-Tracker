package com.example.expensetrackerapp.Activites


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.model.CategoryDesign
import com.example.expensetrackerapp.model.Expenses
import com.example.expensetrackerapp.ui.theme.Bills_green
import com.example.expensetrackerapp.ui.theme.Blue_tined_white
import com.example.expensetrackerapp.ui.theme.Deep_blue
import com.example.expensetrackerapp.ui.theme.Food_orange
import com.example.expensetrackerapp.ui.theme.Gray_icon
import com.example.expensetrackerapp.ui.theme.Health_red
import com.example.expensetrackerapp.ui.theme.Navy_blue
import com.example.expensetrackerapp.ui.theme.Other_gray
import com.example.expensetrackerapp.ui.theme.Primary_blue
import com.example.expensetrackerapp.ui.theme.Pure_white
import com.example.expensetrackerapp.ui.theme.Shopping_purple
import com.example.expensetrackerapp.ui.theme.Travel_blue
import com.example.expensetrackerapp.utils.HomeState
import com.example.expensetrackerapp.viewmodel.HomeViewmodel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeActivity(navController: NavController,viewmodel: HomeViewmodel) {
    val homeState by viewmodel.homeState.collectAsState()
    val userName by viewmodel.userName.collectAsState()
    var loading by remember {
        mutableStateOf(false)
    }

    var expenseList by remember {
        mutableStateOf(emptyList<Expenses>())
    }

    LaunchedEffect(homeState) {

        when(val state = homeState)
        {
            is HomeState.HomeLoading -> loading = true
            is HomeState.HomeSuccess -> {
                 loading = false
                 expenseList = state.data
                android.util.Log.d("HOME_DATA", "Fetched: ${state.data.size}")
            }
            is HomeState.HomeFailure ->{
            //  val errorMessage =  (homeState as HomeState.HomeFailure).message
                loading = false
                android.util.Log.e("HOME_DATA", state.message)
            }
        }

    }

    val totalAmount = expenseList.sumOf {
        it.amount.toIntOrNull()?:0
    }


    fun categoryExpense(expenseList: List<Expenses>,category: String): Int
    {
       return expenseList.filter {
            it.category == category
        }
            .sumOf {
                it.amount.toIntOrNull()?:0
            }
    }


    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            ) {


                Column (modifier = Modifier.weight(1f).clickable{}.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Icon(
                        painter = painterResource(R.drawable.home),
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text("Home", fontSize = 12.sp)
                }

                Column (modifier = Modifier.weight(1f).clickable{}.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Icon(
                        painter = painterResource(R.drawable.expense),
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text("Expense", fontSize = 12.sp)
                }

                FloatingActionButton(onClick = {
                    navController.navigate("addExpense")
                },
                    shape = CircleShape,
                    containerColor = Primary_blue) {
                    Icon(imageVector = Icons.Default.Add,null, tint = Pure_white)
                }


                    Column(modifier = Modifier.weight(1f)
                        .clickable{}
                        .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Icon(
                            painter = painterResource(R.drawable.analytics),
                            contentDescription = null,
                            tint = Color.Black,
                        )

                        Text("Analytics", fontSize = 12.sp)
                    }


                Column (modifier = Modifier.weight(1f).clickable{}.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text("Settings", fontSize = 12.sp)
                }
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Blue_tined_white)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Deep_blue, Primary_blue
                            )
                        ), shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp)
                    )
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Column {
                            Text(
                                "Hello,",
                                fontSize = 18.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                userName,
                                fontSize = 28.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(54.dp),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(modifier = Modifier.padding(start = 24.dp)) {
                        Text(
                            text = "Keep track of your expense",
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            color = Color.White
                        )
                        Text(
                            text = "and build a better tomorrow",
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            color = Color.White
                        )
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(Navy_blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 110.dp, start = 24.dp, end = 24.dp)
                        .height(120.dp)
                        .offset(y = 80.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Total Expense", color = Color.White, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "৳ $totalAmount",
                                color = Color.White,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(" +12%", color = Color.Green, fontSize = 12.sp)
                                Spacer(modifier = Modifier.padding(horizontal = 7.dp))
                                Text("than last week", color = Color.Gray, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.12f),
                                    CircleShape
                                ), contentAlignment = Alignment.Center
                        )
                        {
                            Image(
                                painter = painterResource(R.drawable.expenses),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)
                            )
                        }

                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 90.dp)
            ) {
                Text(
                    "Categories",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text("View All >", color = Primary_blue)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                CategoryId(R.drawable.food, Food_orange, "Food", categoryExpense(expenseList,"Food").toString())
                CategoryId(R.drawable.travel, Travel_blue, "Travel", categoryExpense(expenseList,"Travel").toString())
                CategoryId(R.drawable.shopping, Shopping_purple, "Shopping", categoryExpense(expenseList,"Shopping").toString())
                CategoryId(R.drawable.bills, Bills_green, "Bills", categoryExpense(expenseList,"Bills").toString())
                CategoryId(R.drawable.health, Health_red, "Health", categoryExpense(expenseList,"Health").toString())
                CategoryId(R.drawable.other, Gray_icon, "Other", categoryExpense(expenseList,"Other").toString())
            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(260.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(Pure_white)
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                ) {
                    Text("Recent Expenses", fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))

                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(expenseList) { expense ->
                        val myDesign = design(expense)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 16.dp, top = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 48.dp, height = 48.dp)
                                    .background(
                                        color = myDesign.color.copy(alpha = 0.2f)  ,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Icon(
                                    painter = painterResource(myDesign.image),
                                    contentDescription = null, tint = myDesign.color
                                )
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(expense.title, color = Color.Black, fontSize = 14.sp)
//                            Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                Row {
                                    Text(expense.category, fontSize = 10.sp)
                                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                    Text(expense.date, fontSize = 10.sp)
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text("৳ "+expense.amount)
                        }
                        Divider(modifier = Modifier.fillMaxWidth().padding(start = 70.dp, end = 48.dp, top = 2.dp), thickness = 1.dp, color = Gray_icon,)
                    }
                }

            }

        }
    }
}

@Composable
fun CategoryId(image: Int, color: Color, text: String, expense: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(width = 48.dp, height = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = color.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
            )
            {
                IconButton(onClick = {})
                {
                    Icon(painter = painterResource(image),
                        contentDescription = null,
                        tint = color
                        )
                }

            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(3.dp))
        Text("৳" + expense, color = Color.Black, fontSize = 10.sp)
    }
}
//val options:List<String> = listOf("Food","Travel","Shopping","Bills","Health","Other")




fun design(expenses: Expenses): CategoryDesign
{
    when(expenses.category)
    {
        "Food" ->{
            return CategoryDesign(Food_orange, R.drawable.food)
        }
        "Travel" ->{
            return CategoryDesign(Travel_blue,R.drawable.travel)
        }
        "Shopping" ->{
            return CategoryDesign(Shopping_purple,R.drawable.shopping)
        }
        "Bills" ->{
            return CategoryDesign(Bills_green,R.drawable.bills)
        }
        "Health" ->{
            return CategoryDesign(Health_red,R.drawable.health)
        }
        else -> {
            return CategoryDesign(Other_gray, R.drawable.other)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun showHome() {
//    HomeActivity(navController = NavController)
//}