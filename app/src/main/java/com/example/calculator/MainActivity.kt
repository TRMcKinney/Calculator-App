package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set content with Jetpack Compose
        setContent {
            CalculatorTheme {
                RandomNamePickerScreen()
            }
        }
    }
}

@Composable
fun RandomNamePickerScreen() {
    // List of names to randomly pick from
    val names = listOf("Alice", "Bob", "Charlie", "Diana", "Eve")

    // State variable to hold the currently selected name
    var selectedName by remember { mutableStateOf("Hello, my name is: ") }

    // Layout with a column to position the text and button vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text that shows the selected name
        Text(text = selectedName)

        Spacer(modifier = Modifier.height(16.dp))

        // Button that when clicked selects a random name
        Button(onClick = {
            // Pick a random name from the list
            val randomName = names[Random.nextInt(names.size)]
            // Update the displayed text with the selected name
            selectedName = "Hello, my name is: $randomName"
        }) {
            Text(text = "Pick a Random Name")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RandomNamePickerPreview() {
    CalculatorTheme {
        RandomNamePickerScreen()
    }
}