package com.example.jettipapp.ui.theme

import androidx.compose.material3.CardColors
import androidx.compose.ui.graphics.Color

fun customizedCardColors(contentColor: Color = Color.Black, containerColor :Color = Color.White, ): CardColors {
    return CardColors(contentColor = contentColor, containerColor = containerColor,
        disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)
}