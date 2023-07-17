package com.anpe.bilibiliandyou.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp


@Composable
fun TextIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    iconTint: Color,
    text: String? = null,
    contentDescription: String? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            tint = iconTint,
            contentDescription = contentDescription,
        )
        text?.let {
            Text(text = text, fontSize = 10.sp)
        }
    }
}