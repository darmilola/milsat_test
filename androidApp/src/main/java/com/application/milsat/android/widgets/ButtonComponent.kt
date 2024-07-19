package com.application.milsat.android.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
public fun ButtonComponent(modifier: Modifier, buttonText: String,onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
    ){
        TextComponent(
            text = buttonText, fontSize = 16)
    }
}