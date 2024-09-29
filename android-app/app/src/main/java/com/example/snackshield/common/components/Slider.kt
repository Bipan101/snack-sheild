package com.example.snackshield.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SliderComp(
    identifier: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    start: Float,
    end: Float
) {
    Column {
        Text(
            text = "$identifier: ${value.toInt()}",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )
        Spacing(height = 8)
        Slider(value = value, onValueChange = onValueChange, valueRange = start..end)
        Spacing(height = 16)
    }
}