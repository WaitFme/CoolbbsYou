package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy


@Composable
fun ResponsiveLayout(
    modifier: Modifier = Modifier,
    changeValue: Dp = 800.dp,
//    topBar: @Composable () -> Unit = {},
//    bottomBar: @Composable () -> Unit = {},
    railBar: (@Composable () -> Unit)? = null,
    detailsBlock: (@Composable () -> Unit)? = null,
//    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val configuration = LocalConfiguration.current

        val changeState = configuration.screenWidthDp.dp >= changeValue

        Row(modifier = Modifier.fillMaxSize()) {
            if (railBar != null) {
                if (changeState) {
                    railBar()
                }
            }

            Column(
                modifier = if (changeState) modifier.width(400.dp) else modifier,
                content = {
                    content()
                }
            )

            if (detailsBlock != null && changeState) {
                detailsBlock()
            }
        }
    }
}

@Composable
fun TwoPaneResponsiveLayout(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    railBar: (@Composable () -> Unit)? = null,
    isDetailOpen: Boolean,
    setIsDetailOpen: (Boolean) -> Unit,
    list: @Composable (isDetailVisible: Boolean) -> Unit,
    detail: @Composable (isListVisible: Boolean) -> Unit,
) {
    // Query for the current window size class
    val widthSizeClass by rememberUpdatedState(windowSizeClass.widthSizeClass)

    /**
     * The index of the currently selected word, or `null` if none is selected
     */
    var selectedWordIndex: Int? by rememberSaveable { mutableStateOf(null) }

    /**
     * True if the detail is currently open. This is the primary control for "navigation".
     */
    Row(modifier = Modifier.fillMaxSize()) {
        if (railBar != null && widthSizeClass == WindowWidthSizeClass.Expanded) {
            railBar()
        }

        val configuration = LocalConfiguration.current

        val listWidth = (configuration.screenWidthDp / 3).coerceAtLeast(375).coerceAtMost(425)

        ListDetail(
            modifier = modifier,
            isDetailOpen = isDetailOpen,
            setIsDetailOpen = setIsDetailOpen,
            showListAndDetail = widthSizeClass == WindowWidthSizeClass.Expanded,
            detailKey = selectedWordIndex,
            list = list,
            detail = detail,
            twoPaneStrategy = HorizontalTwoPaneStrategy(
                splitOffset = listWidth.dp,
            ),
            displayFeatures = displayFeatures
        )
    }
}