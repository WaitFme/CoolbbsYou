package com.anpe.coolbbsyou.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun CustomProgress(
    modifier: Modifier = Modifier,
    currentValue: Int,
    maxValue: Int,
    strokeWidth: Float = 20f,
    primaryColor: Color = Color.White,
    secondaryColor: Color = Color.Gray
) {
    var maxWidth by remember {
        mutableStateOf(1f)
    }

    val currentWidth by animateFloatAsState(
        targetValue = if (maxValue == 0 && currentValue == 0)
            0f
        else
            maxWidth * (currentValue / maxValue.toFloat()),
        label = ""
    )

    Canvas(
        modifier = modifier
    ) {
        // 画布的高
        val canvasHeight = size.height

        // 画布的宽
        val canvasWidth = size.width

        maxWidth = canvasWidth

        drawLine(
            color = secondaryColor,
            start = Offset(0f, 0f),
            end = Offset(canvasWidth, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        drawLine(
            color = primaryColor,
            start = Offset(0f, 0f),
            end = Offset(currentWidth, 0f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}