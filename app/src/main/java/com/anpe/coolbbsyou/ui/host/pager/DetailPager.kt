package com.anpe.coolbbsyou.ui.host.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.data.remote.domain.details.DetailsModel
import com.anpe.coolbbsyou.data.remote.domain.reply.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.ReplyState
import com.anpe.coolbbsyou.ui.host.innerScreen.manager.InnerScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.DialogImage
import com.anpe.coolbbsyou.ui.view.HtmlText
import com.anpe.coolbbsyou.ui.view.MyScaffold
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import com.anpe.coolbbsyou.util.Utils.Companion.isTable
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval
import kotlinx.coroutines.launch

@Composable
fun DetailPager(
    modifier: Modifier = Modifier,
    navControllerInnerScreen: NavHostController,
    detailsModel: DetailsModel,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    Surface(modifier = Modifier.offset(1.dp)) {
        val scope = rememberCoroutineScope()

        val replyState by viewModel.replyState.collectAsState()

        val followModel by viewModel.followState.collectAsState()

        val likeState by viewModel.likeState.collectAsState()

        val widthSizeClass by rememberUpdatedState(newValue = windowSizeClass.widthSizeClass)

        val lazyPagingItems = (replyState as ReplyState.Success).pager.collectAsLazyPagingItems()

        val followStatus = detailsModel.data.userAction.follow or followModel.data == 1

        MyScaffold(
            topBar = {
                TopBar(
                    userAvatar = detailsModel.data.userAvatar,
                    username = detailsModel.data.username,
                    deviceTitle = detailsModel.data.deviceTitle,
                    followStatus = followStatus,
                    likeStatus = detailsModel.data.userAction.like == 1 || likeState.isLike,
                    setIsDetailOpen = setIsDetailOpen,
                    onFollow = {
                        scope.launch {
                            viewModel.channel.send(
                                if (followStatus) MainEvent.Unfollow(detailsModel.data.uid) else MainEvent.Follow(
                                    detailsModel.data.uid
                                )
                            )
                        }
                    },
                    onLike = {
                        scope.launch {
                            viewModel.channel.send(
                                if (it) MainEvent.Unlike(detailsModel.data.id) else MainEvent.Like(
                                    detailsModel.data.id
                                )
                            )
                        }
                    },
                    onClickUser = {
                        scope.launch {
                            viewModel.channel.send(MainEvent.GetSpace(detailsModel.data.uid))
                            navControllerInnerScreen.navigate(InnerScreenManager.UserSpaceInnerScreen.route)
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = modifier
                        .padding(top = it.calculateTopPadding())
                ) {
                    if (widthSizeClass == WindowWidthSizeClass.Expanded) {
                        Row {
                            ContentBlock(
                                modifier = Modifier
                                    .weight(1.5f)
                                    .verticalScroll(rememberScrollState()),
                                detailsModel = detailsModel
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .fillMaxHeight()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                        .alpha(0.5f)
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.CenterStart),
                                        text = "共${detailsModel.data.replynum}回复"
                                    )
                                }
                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(bottom = 15.dp),
                                    content = {
                                        items(lazyPagingItems) {
                                            it?.apply {
                                                ReplyItem(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    data = this,
                                                    itemPadding = PaddingValues(
                                                        15.dp,
                                                        7.dp,
                                                        15.dp,
                                                        15.dp
                                                    ),
                                                    onClickUser = {
                                                        scope.launch {
                                                            viewModel.channel.send(MainEvent.GetSpace(it))
                                                            navControllerInnerScreen.navigate(InnerScreenManager.UserSpaceInnerScreen.route)
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(bottom = 15.dp),
                            content = {
                                item {
                                    ContentBlock(
                                        modifier = Modifier,
                                        detailsModel = detailsModel
                                    )
                                }
                                items(lazyPagingItems) {
                                    it?.apply {
                                        ReplyItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            data = this,
                                            itemPadding = PaddingValues(
                                                15.dp,
                                                7.dp,
                                                15.dp,
                                                15.dp
                                            )
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            },
        )
    }
}

@Composable
private fun ContentBlock(
    modifier: Modifier = Modifier,
    detailsModel: DetailsModel
) {
    Column(modifier = modifier) {
        var status by remember {
            mutableStateOf(false)
        }

        var initialPage by remember {
            mutableIntStateOf(0)
        }

        HtmlText(
            modifier = Modifier
                .padding(15.dp, 10.dp, 15.dp, 0.dp),
            fontSize = 16.sp,
            htmlText = detailsModel.data.message, openLink = {
                println(it)
            }
        )

        if (detailsModel.data.picArr.isNotEmpty()) {
            NineImageGrid(
                modifier = Modifier
                    .padding(13.dp, 10.dp, 13.dp, 0.dp)
                    .width(500.dp),
                list = detailsModel.data.picArr,
                itemPadding = PaddingValues(2.dp),
                itemClip = RoundedCornerShape(10.dp),
                onClick = {
                    initialPage = it
                    status = !status
                }
            )
        }

        Text(
            modifier = Modifier
                .padding(15.dp, 10.dp, 15.dp, 15.dp),
            text = "${(detailsModel.data.createTime.toLong() * 1000).timeStampInterval(System.currentTimeMillis())} 发布于${detailsModel.data.ipLocation} 共${detailsModel.data.replynum}回复",
            fontSize = 12.sp
        )

        if (status) {
            DialogImage(detailsModel.data.picArr, initialPage, onDismissRequest = {
                status = false
            })
        }
    }
}

@Composable
private fun ReplyItem(
    modifier: Modifier = Modifier,
    data: Data,
    itemPadding: PaddingValues = PaddingValues(15.dp, 5.dp, 15.dp, 0.dp),
    onClickUser: (Int) -> Unit = {},
    onClickReply: (Int) -> Unit = {}
) {
    ConstraintLayout(modifier = modifier.padding(itemPadding)) {
        val (avatarRef, usernameRef, messageRef, picRef, funRef, replyRowsRef) = createRefs()

        val verifyColor = when (data.userInfo.verifyIcon) {
            "v_yellow" -> Color.Yellow
            "v_green" -> Color.Green
            "v_blue" -> Color.Blue
            else -> MaterialTheme.colorScheme.primary
        }

        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickableNoRipple {
                    onClickUser(data.uid)
                }
                .constrainAs(avatarRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.userAvatar)
                .crossfade(true)
                .build(),
            contentDescription = "avatar"
        )

        Text(
            modifier = Modifier
                .constrainAs(usernameRef) {
                    start.linkTo(avatarRef.end, 10.dp)
                    top.linkTo(avatarRef.top)
                },
            fontSize = 15.sp,
            text = data.username,
            color = verifyColor
        )

        HtmlText(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messageRef) {
                    start.linkTo(avatarRef.end, 10.dp)
                    top.linkTo(usernameRef.bottom, 5.dp)
                    end.linkTo(parent.end)
                    width = Dimension.preferredWrapContent
                },
            fontSize = 14.sp, // 13sp
            lineHeight = 20.sp,
            htmlText = data.message,
            openLink = { },
            color = if (!isSystemInDarkTheme()) Color.Black else Color.White
        )

        if (data.pic.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(150.dp)
                    .constrainAs(picRef) {
                        start.linkTo(avatarRef.end, 10.dp)
                        top.linkTo(messageRef.bottom, 10.dp)
//                            end.linkTo(parent.end)
//                            width = Dimension.preferredWrapContent
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.pic)
                    .crossfade(true)
                    .build(),
                contentDescription = "reply pic"
            )
        }

        if (data.replyRows.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .constrainAs(replyRowsRef) {
                        start.linkTo(avatarRef.end, 10.dp)
                        top.linkTo(
                            if (data.pic.isEmpty()) messageRef.bottom else picRef.bottom,
                            10.dp
                        )
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    },
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(5.dp)) {
                    data.replyRows.forEach {
                        ClickableText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape((7.5).dp))
                                .clickable { }
                                .padding(3.dp, 2.dp, 3.dp, 2.dp),
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(it.username)
                                }
                                append(": ${it.message}")
                            },
                            onClick = {index->
                                if (index >= 1 && index < it.username.length + 1) {
                                    onClickUser(it.uid)
                                } else {
                                    onClickReply(-1)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    userAvatar: String,
    username: String,
    deviceTitle: String,
    followStatus: Boolean,
    likeStatus: Boolean,
    setIsDetailOpen: (Boolean) -> Unit,
    onFollow: () -> Unit,
    onClickUser: () -> Unit,
    onLike: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    var likeStatusInner by remember {
        mutableStateOf(likeStatus)
    }

    TopAppBar(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        navigationIcon = {
            if (!configuration.isTable()) {
                IconButton(onClick = { setIsDetailOpen(false) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "arrowBack"
                    )
                }
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickableNoRipple {
                            onClickUser()
                        },
                    model = ImageRequest.Builder(context)
                        .data(userAvatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = "userAvatar"
                )

                Column {
                    Text(
                        text = username,
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
                    Text(
                        text = deviceTitle,
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp
                    )
                }
            }
        },
        actions = {
            /*IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }*/
            IconButton(onClick = {
                onLike(likeStatusInner)
                likeStatusInner = !likeStatusInner
            }) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "add",
                    tint = if (likeStatusInner) MaterialTheme.colorScheme.primary else LocalContentColor.current
                )
            }
            /*IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "add")
            }*/
            /*IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "add")
            }*/

            TextButton(
                modifier = Modifier
                    .padding(end = 12.dp),
                contentPadding = PaddingValues(2.dp),
                onClick = onFollow,
                content = {
                    Text(modifier = Modifier, text = if (followStatus) "已订阅" else "订阅")
                }
            )
        }
    )
}