package com.anpe.coolbbsyou.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MySheetScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    railBar: @Composable () -> Unit = {},
    sheetContent: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit = {},
    configuration: Configuration,
    changeValue: Dp? = null,
    visible: Boolean,
    cancelable: Boolean = true,
    canceledOnTouchOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row {
                if (changeValue != null) {
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
                        Column(
                            modifier = Modifier.padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                        ) {
                            content()
                        }
                    },
                    floatingActionButton = {
                        floatingActionButton()
                    },
                    bottomBar = {
                        if (changeValue == null) {
                            bottomBar()
                        } else {
                            if (configuration.screenWidthDp.dp < changeValue) {
                                bottomBar()
                            }
                        }
                    }
                )
            }

            BottomSheetDialog(
                modifier = Modifier,
                visible = visible,
                cancelable = cancelable,
                canceledOnTouchOutside = canceledOnTouchOutside,
                onDismissRequest = onDismissRequest,
                content = {
                    sheetContent()
                }
            )
        }
    }
}