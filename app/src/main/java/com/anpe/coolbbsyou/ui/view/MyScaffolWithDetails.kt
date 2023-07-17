package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun MyScaffoldWithDetails(
    modifier: Modifier = Modifier,
    changeValue: Dp = 800.dp,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    railBar: (@Composable () -> Unit)? = null,
    detailsBlock: (@Composable () -> Unit) ? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val configuration = LocalConfiguration.current

        val changeState = configuration.screenWidthDp.dp >= changeValue

        val isDetails = detailsBlock != null

        Row(modifier = Modifier.fillMaxSize()) {
            if (railBar != null) {
                if (changeState) {
                    railBar()
                }
            }

            Scaffold(
                modifier = if (changeState) modifier.width(400.dp) else modifier,
                topBar = {
                    topBar()
                },
                content = {
                    content(it)
                },
                floatingActionButton = {
                    floatingActionButton()
                },
                bottomBar = {
                    if (railBar == null) {
                        bottomBar()
                    } else {
                        if (!changeState) {
                            bottomBar()
                        }
                    }
                }
            )

            if (detailsBlock != null && changeState) {
                detailsBlock()
            }
        }
    }
}