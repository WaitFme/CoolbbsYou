package com.anpe.coolbbsyou.ui.host.pager

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.bilibiliandyou.ui.view.TextDirection
import com.anpe.bilibiliandyou.ui.view.TextIcon
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.remote.domain.index.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.IndexImageState
import com.anpe.coolbbsyou.intent.state.IndexState
import com.anpe.coolbbsyou.ui.host.innerScreen.manager.InnerScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.DialogImage
import com.anpe.coolbbsyou.ui.view.HtmlText
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePager(
    navControllerScreen: NavHostController,
    navControllerInnerScreen: NavHostController,
    navControllerPager: NavHostController,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val indexState by viewModel.indexState.collectAsState()

    val lazyPagingItems = (indexState as IndexState.Success).pager.collectAsLazyPagingItems()

    val indexImageState by viewModel.indexImageState.collectAsState()

    val isNineGrid = when (indexImageState) {
        IndexImageState.ImageRow -> false
        IndexImageState.NineGrid -> true
    }

    var refreshing by remember {
        mutableStateOf(false)
    }
    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            lazyPagingItems.refresh()
            refreshing = false
        }
    })

    Box(Modifier.fillMaxSize()) {
        when (indexState) {
            is IndexState.Error -> {
                val error = (indexState as IndexState.Error).error
                Text(modifier = Modifier.align(Alignment.Center), text = error)
            }

            is IndexState.Idle -> {
                Text(modifier = Modifier.align(Alignment.Center), text = "idle")
            }

            is IndexState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IndexState.Success -> {
                Log.d("stateIndex", "HomePager: Success")


                var index by rememberSaveable { mutableIntStateOf(1) }
                Column {
                    TabRow(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp),
                        selectedTabIndex = index,
                        divider = { }
                    ) {
                        Tab(
                            selected = true,
                            text = { Text(text = "关注") },
                            onClick = { index = 0 }
                        )
                        Tab(
                            selected = false,
                            text = { Text(text = "头条") },
                            onClick = { index = 1 }
                        )
                        Tab(
                            selected = false,
                            text = { Text(text = "话题") },
                            onClick = { index = 2 }
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .pullRefresh(refreshState)
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(15.dp, 0.dp, 15.dp, 10.dp),
                        content = {
                            Log.d("stateIndex", "HomePager: 0")
                            items(lazyPagingItems) {
                                Log.d("stateIndex", "HomePager: ${it._tid}")
                                it.run {
                                    when (entityType) {
                                        "imageCarouselCard_1" -> {
                                            BannerItem(
                                                modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                                                data = this,
                                                onClick = {
                                                    scope.launch {
                                                        val url = entities[0].url
                                                        val substring =
                                                            url.substring(url.indexOf("url=") + 4)
                                                        viewModel.channel.send(
                                                            MainEvent.GetTodayCool(
                                                                url = substring,
                                                                page = 1
                                                            )
                                                        )
                                                        navControllerInnerScreen.navigate(InnerScreenManager.TodaySelectionInnerScreen.route)
                                                    }
                                                },
                                            )
                                        }

                                        "feed" -> {
                                            var likeNum by remember {
                                                mutableStateOf(likenum)
                                            }
                                            var likeStatus by remember {
                                                mutableStateOf(userAction?.let {
                                                    userAction.like == 1
                                                } ?: false)
                                            }
                                            FeedItem(
                                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                                data = this,
                                                isNineGrid = isNineGrid,
                                                likeNum = likeNum,
                                                likeStatus = likeStatus,
                                                onClick = {
                                                    scope.launch {
                                                        viewModel.channel.send(MainEvent.GetDetails(id)) // 48449942
                                                        viewModel.channel.send(MainEvent.GetReply(id))
                                                        setIsDetailOpen(true)
                                                    }
                                                },
                                                onLike = {
                                                    scope.launch {
                                                        if (likeStatus) {
                                                            viewModel.getUnlike(it.id)?.apply {
                                                                if (data != null) {
                                                                    likeNum = data
                                                                    likeStatus = false
                                                                }
                                                            }
                                                        } else {
                                                            viewModel.getLike(it.id)?.apply {
                                                                if (data != null) {
                                                                    likeNum = data
                                                                    likeStatus = true
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            )
                                        }

                                        "imageTextScrollCard" -> {
                                            ImageTextItem(
                                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                                data = this,
                                                onClick = {
                                                    scope.launch {
//                                                id = 48068410
                                                        /*viewModel.channel.send(MainIntent.GetDetails(id))
                                                        if (!configuration.isTable()) {
                                                            navControllerScreen.navigate(ScreenManager.DetailsScreen.route)
                                                        }*/
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                }

                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = refreshing,
                    state = refreshState,
                    contentColor = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BannerItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    val list = mutableListOf<String>()
    data.entities.forEach {
        when (it.title) {
            "今日酷安" -> {
                list.add(it.pic)
            }
            "新机资讯" -> {
                list.add(it.pic)
            }
        }
    }

    val state = rememberPagerState(initialPage = 0) {
        list.size
    }
    HorizontalPager(
        modifier = modifier
            .padding(bottom = 5.dp)
            .clip(RoundedCornerShape(15.dp)),
        state = state,
//        pageSpacing = 10.dp
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickableNoRipple {
                    onClick()
                },
            model = ImageRequest.Builder(context)
                .data(list[it])
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "image"
        )
    }
}

@Composable
private fun FeedItem(
    modifier: Modifier = Modifier,
    data: Data,
    isNineGrid: Boolean,
    likeNum: Int = data.likenum,
    likeStatus: Boolean = false,
    replyNum: Int = data.replynum,
    shareNum: Int = data.shareNum,
    onClick: () -> Unit,
    onLike: (Data) -> Unit = {},
    onReply: (Int) -> Unit = {},
    onShare: (Int) -> Unit = {},
) {
    val context = LocalContext.current

    var status by remember {
        mutableStateOf(false)
    }

    var initialPage by remember {
        mutableStateOf(0)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickableNoRipple {
                onClick()
            },
        shape = RoundedCornerShape(15.dp)
    ) {
        ConstraintLayout {
            val (
                proPicRef,
                nameRef,
                messageRef,
                infoHtmlRef,
                deviceRef,
                picRef,
                hotReplyRef,
                likeRef,
            ) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .constrainAs(proPicRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                    },
                model = ImageRequest.Builder(context)
                    .data(data.userAvatar)
                    .size(100)
                    .crossfade(true)
                    .build(),
                contentDescription = ""
            )

            Text(
                modifier = Modifier
                    .constrainAs(nameRef) {
                        start.linkTo(proPicRef.end, 10.dp)
                        top.linkTo(proPicRef.top)
                    },
                text = data.username,
                fontSize = 15.sp
            )

            Text(
                modifier = Modifier.constrainAs(infoHtmlRef) {
                    start.linkTo(nameRef.start)
                    top.linkTo(nameRef.bottom)
                },
                text = data.infoHtml,
                fontSize = 11.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(deviceRef) {
                        start.linkTo(infoHtmlRef.end, 5.dp)
                        top.linkTo(infoHtmlRef.top)
                    },
                text = data.deviceTitle,
                fontSize = 11.sp
            )

            HtmlText(
                modifier = Modifier
                    .constrainAs(messageRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(proPicRef.bottom, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        this.width = Dimension.matchParent
                    },
                htmlText = data.message,
                openLink = {

                }
            )

            if (data.picArr.isNotEmpty()) {
                if (!isNineGrid) {
                    LazyRow(
                        modifier = Modifier
                            .constrainAs(picRef) {
                                start.linkTo(parent.start, 10.dp)
                                top.linkTo(messageRef.bottom, 10.dp)
                                end.linkTo(parent.end, 10.dp)
                                width = Dimension.matchParent
                            },
                        content = {
                            for ((num, pic) in data.picArr.withIndex()) {
                                item {
                                    AsyncImage(
                                        modifier = Modifier
                                            .clickableNoRipple {
                                                initialPage = num
                                                status = !status
                                            }
                                            .size(100.dp)
                                            .padding(end = 5.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .aspectRatio(1f),
                                        model = ImageRequest.Builder(context)
                                            .data(pic)
                                            .size(500)
                                            .build(),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "image"
                                    )
                                }
                            }
                        })
                } else {
                    NineImageGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .constrainAs(picRef) {
                                start.linkTo(parent.start, 10.dp)
                                top.linkTo(messageRef.bottom, 10.dp)
                                end.linkTo(parent.end, 10.dp)
                                width = Dimension.matchParent
                            },
                        list = data.picArr,
                        onClick = {
                            initialPage = it
                            status = !status
                        }
                    )
                }
            }

            if (data.replyRows.isNotEmpty()) {
                val replyRow = data.replyRows[0]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .constrainAs(hotReplyRef) {
                            start.linkTo(parent.start)
                            top.linkTo(
                                if (data.picArr.isNotEmpty()) picRef.bottom else messageRef.bottom,
                                10.dp
                            )
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "${replyRow.username}: ${replyRow.message}",
                        maxLines = 3,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(likeRef) {
                        start.linkTo(parent.start)
                        top.linkTo(
                            if (data.replyRows.isNotEmpty()) {
                                hotReplyRef.bottom
                            } else if (data.picArr.isNotEmpty()) {
                                picRef.bottom
                            } else {
                                messageRef.bottom
                            },
                            0.dp
                        )
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, 10.dp)
                    }
            ) {
                TextIcon(
                    modifier = Modifier
                        .weight(1f)
                        .clickableNoRipple {
                            onLike(data)
                        },
                    text = likeNum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_thumb_up_alt_24,
                    iconTint = if (likeStatus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceTint
                )
                TextIcon(
                    modifier = Modifier
                        .weight(1f)
                        .clickableNoRipple {
                            onReply(data.id)
                        },
                    text = replyNum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_chat_bubble_24,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    iconId = R.drawable.baseline_share_24,
                    text = shareNum.let { if (it == 0) "Share" else it.toString() },
                    textDirection = TextDirection.Bottom,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    }

    if (status) {
        DialogImage(data.picArr, initialPage, onDismissRequest = {
            status = false
        })
    }
}

@Composable
private fun ImageTextItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp),
            text = "新鲜图文"
        )

        LazyRow(content = {
            items(data.entities) {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickableNoRipple {
                            onClick()
                        }
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f),
                        model = ImageRequest.Builder(context)
                            .data(it.pic)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "image"
                    )
                    HtmlText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(5.dp),
                        htmlText = it.message ?: "NULL",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 15.sp,
                        openLink = {

                        }
                    )
                }
            }
        })
    }
}

private fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>?,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyItemScope.(value: T) -> Unit
) {
    items?.run {
        items(
            count = itemCount,
            key = null
        ) { index ->
            if (this@run[index] != null) {
                itemContent(this@run[index]!!)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RefreshLoadColumn(
    lazyPagingItems: LazyPagingItems<Data>?,
    onRefresh: () -> Unit,
    content: () -> Unit
) {
    var refreshing by rememberSaveable {
        mutableStateOf(true)
    }

    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            onRefresh()
            refreshing = false
        }
    )

    Box(Modifier.fillMaxSize()) {
        var dataList by remember {
            mutableStateOf<LazyPagingItems<Data>?>(lazyPagingItems)
        }
        lazyPagingItems?.apply {
            this
            val data = this[itemCount]
        }
        content()

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = refreshState,
            contentColor = MaterialTheme.colorScheme.surfaceTint
        )
    }
}
