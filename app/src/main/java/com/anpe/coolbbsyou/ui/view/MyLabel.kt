package com.anpe.bilibiliandyou.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyLabel(
    modifier: Modifier = Modifier,
//    margin: PaddingValues = PaddingValues(0.dp),
    contentMargin: PaddingValues = PaddingValues(5.dp, 3.dp, 5.dp, 3.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    radio: Dp = 5.dp,
    text: String,
    fontSize: TextUnit = 13.sp,
    icon: Int? = null,
    iconTint: Color = LocalContentColor.current,
    iconSize: Dp = 15.dp,
    iconMargin: PaddingValues = PaddingValues(end = 3.dp)
) {
    Row(
        modifier = modifier
//            .padding(margin)
            .clip(RoundedCornerShape(radio))
            .background(backgroundColor)
            .padding(contentMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.size(iconSize).padding(iconMargin),
                painter = painterResource(id = it),
                tint = iconTint,
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier,
            text = text,
            fontSize = fontSize
        )
    }
}