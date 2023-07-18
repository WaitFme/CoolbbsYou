package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadableLazyColumn(
    modifier: Modifier = Modifier,
    state: LoadableLazyColumnState,
    refreshing: Boolean,
    loading: Boolean,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loadingContent: (@Composable () -> Unit)? = null,
    content: LazyListScope.() -> Unit,
) {
    val lazyListState = state.lazyListState
    // 获取 lazyList 布局信息
    val listLayoutInfo by remember { derivedStateOf { lazyListState.layoutInfo } }
    Box(
        modifier = modifier
            .pullRefresh(state.pullRefreshState)
    ) {
        LazyColumn(
            contentPadding = contentPadding,
            state = state.lazyListState,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = {
                content()
                item {
                    if (loadingContent != null) {
                        loadingContent()
                    } else {
                        if (loading) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            },
        )
        PullRefreshIndicator(
            refreshing,
            state.pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
    // 上次是否正在滑动
    var lastTimeIsScrollInProgress by remember {
        mutableStateOf(lazyListState.isScrollInProgress)
    }
    // 上次滑动结束后最后一个可见的index
    var lastTimeLastVisibleIndex by remember {
        mutableStateOf(listLayoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
    }
    // 当前是否正在滑动
    val currentIsScrollInProgress = lazyListState.isScrollInProgress
    // 当前最后一个可见的 index
    val currentLastVisibleIndex = listLayoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    if (!currentIsScrollInProgress && lastTimeIsScrollInProgress) {
        if (currentLastVisibleIndex != lastTimeLastVisibleIndex) {
            val isScrollDown = currentLastVisibleIndex > lastTimeLastVisibleIndex
            val remainCount = listLayoutInfo.totalItemsCount - currentLastVisibleIndex - 1
            if (isScrollDown && remainCount <= state.loadMoreState.loadMoreRemainCountThreshold) {
                LaunchedEffect(Unit) {
                    state.loadMoreState.onLoadMore()
                }
            }
        }
        // 滑动结束后再更新值
        lastTimeLastVisibleIndex = currentLastVisibleIndex
    }
    lastTimeIsScrollInProgress = currentIsScrollInProgress
}

@Composable
@ExperimentalMaterialApi
fun rememberLoadableLazyColumnState(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    refreshThreshold: Dp = PullRefreshDefaults.RefreshThreshold,
    refreshingOffset: Dp = PullRefreshDefaults.RefreshingOffset,
    loadMoreRemainCountThreshold: Int = 5,
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LoadableLazyColumnState {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh,
        refreshingOffset = refreshingOffset,
        refreshThreshold = refreshThreshold,
    )

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemScrollOffset = initialFirstVisibleItemScrollOffset,
        initialFirstVisibleItemIndex = initialFirstVisibleItemIndex,
    )

    val loadMoreState = rememberLoadMoreState(loadMoreRemainCountThreshold, onLoadMore)

    return remember(pullRefreshState, lazyListState, loadMoreState) {
        LoadableLazyColumnState(
            lazyListState = lazyListState,
            pullRefreshState = pullRefreshState,
            loadMoreState = loadMoreState,
        )
    }
}

@Composable
fun rememberLoadMoreState(
    loadMoreRemainCountThreshold: Int,
    onLoadMore: () -> Unit
): LoadMoreState {
    return remember {
        LoadMoreState(loadMoreRemainCountThreshold, onLoadMore)
    }
}

@OptIn(ExperimentalMaterialApi::class)
data class LoadableLazyColumnState(
    val lazyListState: LazyListState,
    val pullRefreshState: PullRefreshState,
    val loadMoreState: LoadMoreState,
)

data class LoadMoreState(
    val loadMoreRemainCountThreshold: Int,
    val onLoadMore: () -> Unit
)