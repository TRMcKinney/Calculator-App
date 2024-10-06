package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                NumberGridScreen()
            }
        }
    }
}

@Composable
fun NumberGridScreen() {
    // State to store the currently selected number
    var selectedNumber by remember { mutableStateOf("") }

    // Layout to position buttons and text
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display 4 rows of buttons, 3 buttons per row
        for (row in 0..3) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                for (col in 1..3) {
                    val buttonNumber = (row * 3 + col).toString() // Generate button numbers 1 to 12
                    CircularButton(number = buttonNumber) {
                        selectedNumber = buttonNumber
                    }
                }
            }
        }

        // Spacer between buttons and text
        Spacer(modifier = Modifier.height(32.dp))

        // Display the selected number
        Text(text = "Selected number: $selectedNumber")
    }
}

@Composable
fun CircularButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)  // Size to make the button circular
            .clip(CircleShape)  // Clip the button to be circular
    ) {
        Text(text = number)
    }
}

@Preview(showBackground = true)
@Composable
fun NumberGridScreenPreview() {
    CalculatorTheme {
        NumberGridScreen()
    }
}