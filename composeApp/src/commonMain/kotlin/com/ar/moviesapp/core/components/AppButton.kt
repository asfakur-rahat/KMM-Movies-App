package com.ar.moviesapp.core.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    isUpperCase: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = RoundedCornerShape(25),
    enabled: Boolean = true,
    oncClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = {
            oncClick.invoke()
        },
        shape = shape,
        colors = colors,
        enabled = enabled
    ){
        Text(text = if (isUpperCase) text.uppercase() else text)
    }
}