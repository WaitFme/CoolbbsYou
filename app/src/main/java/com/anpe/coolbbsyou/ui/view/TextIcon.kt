package com.anpe.bilibiliandyou.ui.view

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun TextIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    iconTint: Color,
    textDirection: TextDirection = TextDirection.Bottom,
    text: String? = null,
    fontSize: TextUnit = 10.sp,
    contentDescription: String? = null
) {
    ConstraintLayout(modifier = modifier) {
        val (iconRef, textRef) = createRefs()

        Icon(
            modifier = Modifier.constrainAs(iconRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            painter = painterResource(id = iconId),
            tint = iconTint,
            contentDescription = contentDescription,
        )

        text?.let {
            Text(
                modifier = Modifier.constrainAs(textRef) {
                    when (textDirection) {
                        TextDirection.Bottom -> {
                            top.linkTo(iconRef.bottom, 5.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        TextDirection.End -> {
                            start.linkTo(iconRef.end, 5.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        TextDirection.Start -> {
                            end.linkTo(iconRef.start, 5.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        TextDirection.Top -> {
                            bottom.linkTo(iconRef.top, 5.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    }
                },
                text = text,
                fontSize = fontSize
            )
        }
    }
}

sealed class TextDirection {
    object Start : TextDirection()
    object Top : TextDirection()
    object End : TextDirection()
    object Bottom : TextDirection()
}