package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import coil.compose.AsyncImage

@Composable
fun FullScreenImage(
    modifier: Modifier = Modifier,
    url: String,
    onClick: () -> Unit = {}
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        scale = (zoomChange * scale).coerceAtLeast(1f)
    }

    Surface(
        color = Color.DarkGray,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = 1f
                        offset = Offset.Zero
                    },
                    onTap = {
                        onClick.invoke()
                    }
                )
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .transformable(state = state)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        offset += dragAmount
                    }
                },
            model = url,
            contentDescription = ""
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialogImage(picList: List<String>, initialPage: Int, onDismissRequest: () -> Unit,) {
    /*val pagerState = rememberPagerState(
        initialPage = initialPage,
    )
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(Modifier.fillMaxSize().clickableNoRipple { onDismissRequest() }) {
            HorizontalPager(
                modifier = Modifier.align(Alignment.Center)
                    .clip(RoundedCornerShape(15.dp)),
                pageCount = picList.size,
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                pageSpacing = 10.dp
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(picList[it])
                        .build(),
                    contentDescription = null
                )
            }
        }
    }*/
}