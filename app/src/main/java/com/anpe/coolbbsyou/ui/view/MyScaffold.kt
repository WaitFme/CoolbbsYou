package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyScaffold(
    modifier: Modifier = Modifier,
    changeValue: Dp = 800.dp,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    railBar: (@Composable () -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val configuration = LocalConfiguration.current

    Row(modifier = Modifier.fillMaxSize()) {
        if (railBar != null) {
            if (configuration.screenWidthDp.dp >= changeValue) {
                railBar()
            }
        }
        Scaffold(
            modifier = modifier,
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
                    if (configuration.screenWidthDp.dp < changeValue) {
                        bottomBar()
                    }
                }
            }
        )
    }
}