package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.ui.theme.components.CustomInputText
import com.example.jettipapp.ui.theme.customizedCardColors
import com.example.jettipapp.ui.theme.widgets.RoundIconButton

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
private fun calculateTotalPerPerson(bill:Double, percentage: Float, splitBy:Int): Double =
    (bill + bill * percentage.toDouble()) / splitBy
@Composable
fun MyApp(modifier: Modifier){
    val bill = remember { mutableStateOf("") }
    val validState = remember(bill.value) { bill.value.isNotBlank() }
    val counter = remember { mutableIntStateOf(1) }
    val percentage = remember { mutableFloatStateOf(0f) }
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TipHeader(if(validState) calculateTotalPerPerson (bill= bill.value.toDouble(), percentage = percentage.floatValue, splitBy = counter.intValue)
        else 0.0)
        MainContent(bill, validState, counter, percentage)
    }
}


@Composable
@Preview
fun TipHeader(totalPerPerson: Double=0.0){
    Card(modifier = Modifier
        .fillMaxWidth(0.9f)
        .padding(top = 20.dp)
        .height(160.dp),
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
fun MainContent(bill: MutableState<String> = mutableStateOf(""),
                validState: Boolean=false,
                counter : MutableState<Int> = mutableIntStateOf(1),
                percentage: MutableState<Float> = mutableFloatStateOf(0f)
){
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = customizedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(10.dp).fillMaxWidth(),
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                CustomInputText(
                    valueState = bill,
                    enabled = true,
                    isSingleLine = true,
                    label = "Enter Bill",
                    onAction = KeyboardActions({
                        if (!validState) return@KeyboardActions
                        keyboardController?.hide()
                    }),
                )
            }
            if (validState) {
                Row(modifier = Modifier.padding(13.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Split", modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    ))
                    Spacer(modifier = Modifier.padding(horizontal = 100.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {
                        RoundIconButton(
                            imageVector = Icons.Sharp.Add,
                            onClick = {
                                counter.value++
                            }
                        )
                    }
                    Text(text = "${counter.value}")
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {
                        RoundIconButton(
                            imageVector = ImageVector.vectorResource(id =R.drawable.sharp_remove_24),
                            onClick = {
                                if( counter.value > 1 )
                                    counter.value--
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Tip")
                    Text(text = "$${"%.2f".format(bill.value.toFloat() * percentage.value)}")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${"%.2f".format(percentage.value*100)}%")
                }
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Slider(value =  percentage.value,
                        steps = 5,
                        onValueChange = {
                            percentage.value = it
                        })
                }
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