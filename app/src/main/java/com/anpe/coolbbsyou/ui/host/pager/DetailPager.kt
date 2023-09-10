package com.anpe.coolbbsyou.ui.host.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.data.remote.domain.details.DetailsModel
import com.anpe.coolbbsyou.data.remote.domain.reply.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.ReplyState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.DialogImage
import com.anpe.coolbbsyou.ui.view.HtmlText
import com.anpe.coolbbsyou.ui.view.MyScaffold
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.isTable
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval

@Composable
fun DetailPager(
    modifier: Modifier = Modifier,
    entity: DetailsModel,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit
) {
    Surface(modifier = Modifier.offset(1.dp)) {
        val viewModel: MainViewModel = viewModel()

        val replyState by viewModel.replyState.collectAsState()

        LaunchedEffect(key1 = true, block = {
            viewModel.sendIntent(MainEvent.GetReply(entity.data.id))
        })

        MyScaffold(
            topBar = { TopBar(entity = entity, setIsDetailOpen = setIsDetailOpen) },
            content = {
                Column(
                    modifier = modifier.padding(top = it.calculateTopPadding())
                ) {
                    val widthSizeClass by rememberUpdatedState(newValue = windowSizeClass.widthSizeClass)

                    when (widthSizeClass) {
                        WindowWidthSizeClass.Expanded -> {
                            Row {
                                ContentBlock(
                                    modifier = Modifier
                                        .weight(1.5f)
                                        .verticalScroll(rememberScrollState()),
                                    entity = entity
                                )

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .offset(1.dp)
                                ) {
                                    var pagingItems by remember {
                                        mutableStateOf<LazyPagingItems<Data>?>(null)
                                    }

                                    when (replyState) {
                                        is ReplyState.Error -> {}
                                        is ReplyState.Idle -> {}
                                        is ReplyState.Loading -> {}
                                        is ReplyState.Success -> {
                                            pagingItems =
                                                (replyState as ReplyState.Success).pager.collectAsLazyPagingItems()
                                        }
                                    }

                                    if (pagingItems != null) {
                                        Column(
                                            modifier = Modifier
                                                .background(MaterialTheme.colorScheme.surface)
                                                .fillMaxSize()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(15.dp)
                                                    .alpha(0.5f)
                                            ) {
                                                Text(
                                                    modifier = Modifier.align(Alignment.CenterStart),
                                                    text = "共${entity.data.replynum}回复"
                                                )
                                            }
                                            LazyColumn(
                                                modifier = Modifier.fillMaxWidth(),
                                                contentPadding = PaddingValues(bottom = 15.dp),
                                                content = {
                                                    items(pagingItems!!) {
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
                                }
                            }
                        }

                        else -> {
                            when (replyState) {
                                is ReplyState.Error -> {}
                                is ReplyState.Idle -> {}
                                is ReplyState.Loading -> {}
                                is ReplyState.Success -> {
                                    val pagingItems =
                                        (replyState as ReplyState.Success).pager.collectAsLazyPagingItems()

                                    LazyColumn(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentPadding = PaddingValues(bottom = 15.dp),
                                        content = {
                                            item {
                                                ContentBlock(modifier = Modifier, entity = entity)
                                            }
                                            items(pagingItems) {
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
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun ContentBlock(modifier: Modifier = Modifier, entity: DetailsModel) {
    Column(modifier = modifier) {
        var status by remember {
            mutableStateOf(false)
        }

        var initialPage by remember {
            mutableStateOf(0)
        }

        HtmlText(
            modifier = Modifier
                .padding(15.dp, 15.dp, 15.dp, 0.dp),
            fontSize = 16.sp,
            htmlText = entity.data.message, openLink = {
                println(it)
            }
        )

        if (entity.data.picArr.isNotEmpty()) {
            NineImageGrid(
                modifier = Modifier
                    .padding(13.dp, 10.dp, 13.dp, 0.dp)
//                    .fillMaxWidth()
                    .width(500.dp),
                list = entity.data.picArr,
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
            text = "${(entity.data.createTime.toLong() * 1000).timeStampInterval(System.currentTimeMillis())} 发布于${entity.data.ipLocation}",
            fontSize = 12.sp
        )

        if (status) {
            DialogImage(entity.data.picArr, initialPage, onDismissRequest = {
                status = false
            })
        }
    }
}

@Composable
private fun ReplyItem(
    modifier: Modifier = Modifier,
    data: Data,
    itemPadding: PaddingValues = PaddingValues(15.dp, 5.dp, 15.dp, 0.dp)
) {
    ConstraintLayout(modifier = modifier.padding(itemPadding)) {
        val (avatarRef, usernameRef, messageRef, picRef, funRef, replyRowsRef) = createRefs()

        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
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
            color = if (data.userInfo.verifyStatus == 1) {
                if (data.userInfo.verifyTitle == "酷安认证: 酷安员工") {
                    Color.Green
                } else {
                    Color.Yellow
                }
            } else {
                MaterialTheme.colorScheme.primary
            }
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
            openLink = {},
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
                        Text(
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
                            fontSize = 13.sp,
                            lineHeight = 17.sp
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
    entity: DetailsModel,
    setIsDetailOpen: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    TopAppBar(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .offset(0.dp, (-1).dp),
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
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context)
                        .data(entity.data.userAvatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = "userAvatar"
                )

                Column {
                    Text(
                        text = entity.data.username,
                        fontSize = 16.sp
                    )
                    Text(
                        text = entity.data.deviceTitle,
                        fontSize = 12.sp
                    )
                }
            }
        },
        actions = {
            /*IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }*/
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "add")
            }
            /*IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "add")
            }*/
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "add")
            }

            TextButton(
                modifier = Modifier
                    .padding(end = 12.dp),
                contentPadding = PaddingValues(2.dp),
                onClick = {
                    // 订阅
                }) {
                Text(modifier = Modifier, text = "订阅")
            }
        }
    )
}