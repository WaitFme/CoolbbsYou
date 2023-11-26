package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple

@Composable
fun NineImageGrid(
    modifier: Modifier = Modifier,
    list: List<String>,
    itemPadding: PaddingValues = PaddingValues(0.dp),
    itemClip: Shape = RoundedCornerShape(0.dp),
    onClick: (Int) -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        val size = list.size

        val cell = if (size <= 3) {
            0
        } else if (size <= 6) {
            1
        } else {
            2
        }

        for (index in 0..cell) {
            Row {
                for (j in (0 + index * 3)..(2 + index * 3)) {
                    if (j < size) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(itemPadding)
                                .clip(itemClip)
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickableNoRipple {
                                    onClick(j)
                                },
                            model = ImageRequest.Builder(context)
                                .data(list[j])
                                .size(500)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "image"
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable fun TestNine(
    modifier: Modifier = Modifier,
    list: List<String>,
    itemPadding: PaddingValues = PaddingValues(0.dp),
    itemClip: Shape = RoundedCornerShape(0.dp),
    onClick: (Int) -> Unit
) {
    val context = LocalContext.current

    LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
        list.forEachIndexed { index, url ->
            item(index) {
                if (index < 9) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(itemPadding)
                            .clip(itemClip)
                            .aspectRatio(1f)
                            .clickableNoRipple {
                                onClick(index)
                            },
                        model = ImageRequest.Builder(context)
                            .data(url)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "image"
                    )
                }
            }
        }
    })
}