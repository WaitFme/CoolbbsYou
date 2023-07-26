package com.anpe.coolbbsyou.ui.pager

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.bilibiliandyou.ui.view.TextDirection
import com.anpe.bilibiliandyou.ui.view.TextIcon
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.index.Data
import com.anpe.coolbbsyou.network.data.state.IndexImageState
import com.anpe.coolbbsyou.network.data.state.IndexState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.pager.manager.PagerManager
import com.anpe.coolbbsyou.ui.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import com.anpe.coolbbsyou.util.Utils.Companion.isTable
import com.anpe.coolbbsyou.util.Utils.Companion.richToString
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePager(
    navControllerScreen: NavHostController,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val indexState by viewModel.indexState.collectAsState()

    val indexImageState by viewModel.indexImageState.collectAsState()

    var id by remember {
        mutableStateOf(-1)
    }

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
            viewModel.channel.send(MainIntent.GetIndex)
        }
    })

    Box(Modifier.fillMaxSize()) {
        val list: List<Data> = listOf()
        var dataList by remember {
            mutableStateOf(list)
        }

        when (indexState) {
            is IndexState.Error -> {
                refreshing = false
                val error = (indexState as IndexState.Error).error
                Text(modifier = Modifier.align(Alignment.Center), text = error)
            }

            is IndexState.Idle -> {
                refreshing = false
                Text(modifier = Modifier.align(Alignment.Center), text = "idle")
            }

            is IndexState.Loading -> {
                if (dataList.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is IndexState.Success -> {
                refreshing = false
                dataList = (indexState as IndexState.Success).indexEntity.data
            }

        }

        LazyColumn(
            modifier = Modifier
                .pullRefresh(refreshState)
                .fillMaxHeight(),
            contentPadding = PaddingValues(15.dp, 0.dp, 15.dp, 10.dp),
            content = {
                items(items = dataList) {
                    IndexItems(
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        data = it,
                        isNineGrid = isNineGrid,
                        onClick = {
                            id = it.id
                            when (it.entityType) {
                                "imageCarouselCard_1" -> {
                                    val url = it.entities[0].url
                                    val substring = url.substring(url.indexOf("url=") + 4)
                                    navController.navigate("${PagerManager.TodayCoolPager.route}/$substring")
                                }

                                "feed" -> {
                                    scope.launch {
                                        if (!configuration.isTable()) {
                                            navControllerScreen.navigate("${ScreenManager.DetailsScreen.route}/$id")
                                        } else {
                                            viewModel.channel.send(MainIntent.GetDetails(id))
                                        }
                                    }
                                }

                                "imageTextScrollCard" -> {
                                }
                            }
                        }
                    )
                }
            }
        )

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = refreshState,
            contentColor = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Composable
private fun IndexItems(
    modifier: Modifier = Modifier,
    data: Data,
    isNineGrid: Boolean,
    onClick: () -> Unit
) {
    when (data.entityType) {
        "imageCarouselCard_1" -> {
            BannerItem(modifier = modifier, data = data, onClick = onClick)
        }
        "feed" -> {
            IndexItem(modifier = modifier, data = data, isNineGrid = isNineGrid, onClick = onClick)
        }
        "imageTextScrollCard" -> {
            ImageTextItem(modifier = modifier, data = data, onClick = onClick)
        }
    }
}

@Composable
private fun BannerItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier
            .padding(bottom = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickableNoRipple {
                onClick()
            },
        model = ImageRequest.Builder(context)
            .data(data.entities[0].pic)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = "image"
    )
}

@Composable
private fun IndexItem(
    modifier: Modifier = Modifier,
    data: Data,
    isNineGrid: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .clickableNoRipple {
                onClick()
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)
    ) {
        ConstraintLayout {
            val (
                proPicRef,
                nameRef,
                messageRef,
                infoHtmlRef,
                deviceRef,
                deviceLabelRef,
                picRef,
                hotReplyRef,
                likeRef,
                replyRef
            ) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .constrainAs(proPicRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
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
                    .padding(top = 10.dp)
                    .constrainAs(nameRef) {
                        start.linkTo(proPicRef.end)
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
                text = data.infoHtml.richToString(),
                fontSize = 13.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .constrainAs(deviceRef) {
                        start.linkTo(infoHtmlRef.end)
                        top.linkTo(nameRef.bottom)
                    },
                text = data.deviceTitle,
                fontSize = 13.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 10.dp)
                    .constrainAs(messageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(proPicRef.bottom)
                        end.linkTo(parent.end)
                        this.width = Dimension.matchParent
                    },
                text = data.message.richToString()
            )

            if (data.picArr.isNotEmpty()) {
                if (!isNineGrid) {
                    LazyRow(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 10.dp)
                            .constrainAs(picRef) {
                                start.linkTo(parent.start)
                                top.linkTo(messageRef.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.matchParent
                            },
                        content = {
                            items(data.picArr) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(3.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .aspectRatio(1f),
                                    model = ImageRequest.Builder(context)
                                        .data(it)
                                        .size(500)
                                        .build(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "image"
                                )
                            }
                        })
                } else {
                    NineImageGrid(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .constrainAs(picRef) {
                                start.linkTo(parent.start)
                                top.linkTo(messageRef.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.matchParent
                            },
                        list = data.picArr
                    )
                }
            }

            if (data.replyRows.isNotEmpty()) {
                val replyRow = data.replyRows[0]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 0.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .constrainAs(hotReplyRef) {
                            start.linkTo(parent.start)
                            top.linkTo(if (data.picArr.isNotEmpty()) picRef.bottom else messageRef.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "${replyRow.username}: ${replyRow.message}"
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(0.dp, 5.dp, 0.dp, 10.dp)
                    .constrainAs(likeRef) {
                        start.linkTo(parent.start)
                        top.linkTo(
                            if (data.replyRows.isNotEmpty()) {
                                hotReplyRef.bottom
                            } else if (data.picArr.isNotEmpty()) {
                                picRef.bottom
                            } else {
                                messageRef.bottom
                            }
                        )
                        end.linkTo(parent.end)
                    }
            ) {
                TextIcon(
                    modifier = Modifier.weight(1f),
                    text = data.likenum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_thumb_up_alt_24,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    text = data.replynum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_chat_bubble_24,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    iconId = R.drawable.baseline_share_24,
                    text = "Share",
                    textDirection = TextDirection.Bottom,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    }
}

@Composable
private fun ImageTextItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 5.dp),
            text = "新鲜图文"
        )

        LazyRow(content = {
            items(data.entities) {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(5.dp)
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
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "image"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        text = it.message.richToString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }
        })
    }
}
