package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.ui.theme.customizedCardColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTipAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier){
    val bill = remember { mutableDoubleStateOf(0.0) }
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TipHeader(bill.doubleValue+bill.doubleValue/3)
        MainContent(bill)
    }
}

@Composable
@Preview
fun TipHeader(totalPerPerson: Double=0.0){
    Card(modifier = Modifier.fillMaxWidth(0.9f).padding(top = 20.dp).height(160.dp),
        colors = CardColors(contentColor = Color.Black, containerColor = Color(0xFFE9D7F7),
            disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Total Per Person",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineSmall)
            Text(text = "$${"%.2f".format(totalPerPerson)}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Composable
@Preview
fun MainContent(bill: MutableState<Double> = mutableDoubleStateOf(0.0)){
    val percentage:Double=(100.0/3)
    Card(modifier = Modifier.padding(10.dp).height(300.dp).fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = customizedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            ) {
            Row (
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center,
                ) {
                TextField(
                    value = bill.value.toString(),
                    onValueChange = { bill.value = it.toDouble() },
                    label = { Text("Enter Bill") },
                    modifier = Modifier.fillMaxWidth(0.94f),
                )
            }
            Row(modifier = Modifier.fillMaxWidth(0.8f).padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Tip")
                Text(text = "$${"%.2f".format(bill.value*percentage/100)}")
            }
            Row(modifier = Modifier.fillMaxWidth().padding(15.dp),
                horizontalArrangement = Arrangement.Center) {
                Text(text = "${"%.2f".format(percentage)}%")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipAppTheme {
        Greeting("Android")
    }
}