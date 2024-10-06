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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                CalculatorUI()
            }
        }
    }
}

@Composable
fun CalculatorUI() {
    // State to store the button inputs (expression)
    var inputs by remember { mutableStateOf("") }

    // State to store the result
    var result by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom, // Align buttons to the bottom
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the inputs and result
        DisplaySection(inputs, result)

        Spacer(modifier = Modifier.height(16.dp)) // Spacer between display and buttons

        // First row of buttons
        CalculatorRow(
            listOf("AC", "%", "⌫", "÷"),
            inputs = inputs,
            onUpdateInputs = { inputs = it },
            onUpdateResult = { result = it }
        )

        // Second row of buttons
        CalculatorRow(
            listOf("7", "8", "9", "×"),
            inputs = inputs,
            onUpdateInputs = { inputs = it },
            onUpdateResult = { result = it }
        )

        // Third row of buttons
        CalculatorRow(
            listOf("4", "5", "6", "-"),
            inputs = inputs,
            onUpdateInputs = { inputs = it },
            onUpdateResult = { result = it }
        )

        // Fourth row of buttons
        CalculatorRow(
            listOf("1", "2", "3", "+"),
            inputs = inputs,
            onUpdateInputs = { inputs = it },
            onUpdateResult = { result = it }
        )

        // Fifth row of buttons (last row has 00, 0, ".", "=")
        CalculatorRow(
            listOf("00", "0", ".", "="),
            inputs = inputs,
            onUpdateInputs = { inputs = it },
            onUpdateResult = { result = it }
        )
    }
}

@Composable
fun DisplaySection(inputs: String, result: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.End // Align text to the right
    ) {
        // Display all inputs pressed (until AC is pressed)
        Text(
            text = inputs,
            fontSize = 24.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display the result
        Text(
            text = result,
            fontSize = 32.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CalculatorRow(
    buttons: List<String>,
    inputs: String,  // Pass the current input
    onUpdateInputs: (String) -> Unit,
    onUpdateResult: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        for (buttonLabel in buttons) {
            CircularButton(number = buttonLabel) {
                handleButtonClick(
                    label = buttonLabel,
                    inputs = inputs,
                    onUpdateInputs = onUpdateInputs,
                    onUpdateResult = onUpdateResult
                )
            }
        }
    }
}

@Composable
fun CircularButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)  // Adjust size to make the button circular
            .clip(CircleShape),  // Makes the button circular
        contentPadding = PaddingValues(16.dp) // Padding inside the button
    ) {
        Text(text = number)
    }
}

// This function will handle the button clicks and evaluate the expression when "=" is pressed
fun handleButtonClick(
    label: String,
    inputs: String, // Directly pass the current input string
    onUpdateInputs: (String) -> Unit,
    onUpdateResult: (String) -> Unit
) {
    when (label) {
        "AC" -> {
            // Clear the input and reset the result
            onUpdateInputs("") // Reset inputs to an empty string
            onUpdateResult("0") // Reset result to 0
        }
        "⌫" -> {
            // Backspace logic: Remove the last character from the input string
            if (inputs.isNotEmpty()) {
                onUpdateInputs(inputs.dropLast(1))
            }
        }
        "=" -> {
            // Evaluate the expression
            try {
                val expression = ExpressionBuilder(inputs).build() // Use inputs directly
                val result = expression.evaluate().toString() // Evaluate and get result
                onUpdateResult(result) // Update the result state
            } catch (e: Exception) {
                onUpdateResult("Error") // Handle invalid expressions
            }
        }
        else -> {
            // Append the button press to the input string
            onUpdateInputs(inputs + label) // Concatenate inputs with button label
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorUIPreview() {
    CalculatorTheme {
        CalculatorUI()
    }
}