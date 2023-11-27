package com.anpe.coolbbsyou.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgress(
    modifier: Modifier = Modifier,
    currentValue: Int,
    maxValue: Int,
    strokeWidth: Float = 200f,
    primaryColor: Color = Color.White,
    secondaryColor: Color = Color.Gray
) {
    var maxWidth by remember {
        mutableFloatStateOf(1f)
    }

    val currentWidth by animateFloatAsState(
        targetValue = if (maxValue == 0 && currentValue == 0)
            0f
        else
            maxWidth * (currentValue / maxValue.toFloat()),
        label = ""
    )

    Canvas(
        modifier = modifier.width(100.dp).height(10.dp)
    ) {
        // 画布的高
        val canvasHeight = size.height

        // 画布的宽
        val canvasWidth = size.width

        maxWidth = canvasWidth

        /*drawLine(
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
        )*/

        drawRoundRect(
            color = secondaryColor,
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = CornerRadius(30f, 30f)
        )

        drawRoundRect(
            color = primaryColor,
            size = Size(currentWidth, canvasHeight),
            cornerRadius = CornerRadius(30f, 30f)
        )
    }
}

@Composable
fun CustomProgressRect(
    modifier: Modifier = Modifier,
    currentValue: Int,
    maxValue: Int,
    primaryColor: Color = Color.White,
    secondaryColor: Color = Color.Gray
) {
    var maxWidth by remember {
        mutableFloatStateOf(1f)
    }

    val currentWidth by animateFloatAsState(
        targetValue = if (maxValue == 0 && currentValue == 0)
            0f
        else
            maxWidth * (currentValue / maxValue.toFloat()),
        label = ""
    )

    Canvas(
        modifier = modifier.size(100.dp, 10.dp)
    ) {
        // 画布的高
        val canvasHeight = size.height

        // 画布的宽
        val canvasWidth = size.width

        maxWidth = canvasWidth

        drawRoundRect(
            color = secondaryColor,
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = CornerRadius(canvasHeight / 2)
        )

        drawRoundRect(
            color = primaryColor,
            size = Size(currentWidth, canvasHeight),
            cornerRadius = CornerRadius(canvasHeight / 2)
        )
    }
}
