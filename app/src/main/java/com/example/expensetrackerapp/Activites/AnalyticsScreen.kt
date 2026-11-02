package com.example.expensetrackerapp.Activites
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.model.ChartData
import com.example.expensetrackerapp.ui.theme.Bills_green
import com.example.expensetrackerapp.ui.theme.Blue_tined_white
import com.example.expensetrackerapp.ui.theme.Food_orange
import com.example.expensetrackerapp.ui.theme.Health_red
import com.example.expensetrackerapp.ui.theme.Other_gray
import com.example.expensetrackerapp.ui.theme.Primary_blue
import com.example.expensetrackerapp.ui.theme.Pure_white
import com.example.expensetrackerapp.ui.theme.Shopping_purple
import com.example.expensetrackerapp.ui.theme.Travel_blue
import com.example.expensetrackerapp.utils.Collections.BILLS
import com.example.expensetrackerapp.utils.Collections.FOOD
import com.example.expensetrackerapp.utils.Collections.HEALTH
import com.example.expensetrackerapp.utils.Collections.OTHER
import com.example.expensetrackerapp.utils.Collections.SHOPPING
import com.example.expensetrackerapp.utils.Collections.TRAVEL
import com.example.expensetrackerapp.utils.HomeState
import com.example.expensetrackerapp.viewmodel.HomeViewmodel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp
import com.example.expensetrackerapp.ui.theme.Gray_icon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(navController: NavController,homeViewmodel: HomeViewmodel) {

    val homeState by homeViewmodel.homeState.collectAsState()

    val expenseList = (homeState as? HomeState.HomeSuccess)?.data?:emptyList()

    val categories = listOf(
        FOOD to Food_orange,
        TRAVEL to Travel_blue,
        SHOPPING to Shopping_purple,
        BILLS to Bills_green,
        HEALTH to Health_red,
        OTHER to Other_gray,
    )

    val chartData = categories.map { (name,color)->

       val   total = expenseList.filter {
            it.category == name
        }
            .sumOf {
                it.amount.toIntOrNull()?:0
            }

        ChartData(
            name = name,
            amount = total,
            color = color
        )
    }

    Scaffold(
        topBar = {
          TopAppBar(
              title = {
                  Text("Analytics", fontWeight = FontWeight.Bold, color = Pure_white)
              },
              navigationIcon = {
                  IconButton(onClick = {
                      navController.popBackStack()
                  }) {
                      Icon(painter = painterResource(R.drawable.outline_arrow_back_24), contentDescription = "Back button", tint = Pure_white)
                  }
              },
              colors = TopAppBarDefaults.topAppBarColors(Primary_blue)
          )
        }
    ) {innerpadding->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding)
            .background(color = Blue_tined_white)) {


            Row (modifier = Modifier
                .weight(1f)
                .padding(6.dp)){

                Column() {
                    Row {
                        Image(painter = painterResource(R.drawable.chart), contentDescription = "chart", modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        Text("Category-wise Expense", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Text("This month's spending by category", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 26.dp))

                    Row {

                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            DonutChart(chartData)
                        }

                        Column(modifier = Modifier.weight(1.3f)) {
                            chartData.forEach { item->
                                val grandTotal = chartData.sumOf {
                                    it.amount
                                }
                                val percent = if (grandTotal>0)
                                {
                                    (item.amount.toFloat()/grandTotal)*100f
                                }
                                else{
                                    0f
                                }
                                val result = "%.2f".format(percent)
                                // val percent = (item.amount.toFloat()/total)*100f
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.padding(end = 8.dp).size(10.dp).background(item.color,
                                        CircleShape
                                    ))
                                    Text(item.name, fontSize = 12.sp, color = Color.Black, modifier = Modifier.weight(1f))
                                    Text("৳${item.amount}", fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(start = 12.dp).weight(1f))
                                    Text("$result%", fontSize = 12.sp, color = Color.Black, modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }


                }
            }
            Row (modifier = Modifier
                .weight(1f)
                .padding(6.dp)){
                Text("Monthly Expense Trend")
            }
            Row (modifier = Modifier
                .weight(1f)
                .padding(6.dp)){
                Text( "Weekly Expense Trend")
            }

        }
    }
}

@Composable
fun DonutChart(data: List<ChartData>)
{
    Box(modifier = Modifier.size(140.dp),
        contentAlignment = Alignment.Center)
    {
        val total = data.sumOf { it.amount }
        Canvas(modifier = Modifier.fillMaxSize()) {

            var startAngle = -90f

            val strokeWidth = 28.dp.toPx()


            data.forEach { item->

                if (total>0)
                {
                    val sweepAngle = (item.amount.toFloat()/total)*360f
                    val gapAngle = 0.5f


                    drawArc(
                        color = item.color,
                        startAngle = startAngle,
                        sweepAngle = (sweepAngle-gapAngle).coerceAtLeast(0f),
                        useCenter =false,
                        topLeft = Offset(strokeWidth/2,strokeWidth/2),
                        size = Size(size.width-strokeWidth,size.height-strokeWidth),
                        style = Stroke(width = strokeWidth),
                    )

                    startAngle+=sweepAngle
                }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "৳ $total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Total", fontSize = 12.sp)
        }

    }
}