package com.anpe.coolbbsyou.ui.host.screen

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.window.layout.DisplayFeature
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.anpe.bilibiliandyou.ui.view.TextDirection
import com.anpe.bilibiliandyou.ui.view.TextIcon
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.remote.domain.search.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.DetailsState
import com.anpe.coolbbsyou.intent.state.SearchState
import com.anpe.coolbbsyou.intent.state.SuggestState
import com.anpe.coolbbsyou.ui.view.DetailPager
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.DialogImage
import com.anpe.coolbbsyou.ui.view.HtmlText
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.ui.view.TwoPaneResponsiveLayout
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    viewModel: MainViewModel
) {
    var isDetailOpen by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        TwoPaneResponsiveLayout(
            isDetailOpen = isDetailOpen,
            setIsDetailOpen = {
                isDetailOpen = it
            },
            windowSizeClass = windowSizeClass,
            displayFeatures = displayFeatures,
            railBar = {
                RailBar {
                    navControllerScreen.popBackStack()
                }
            },
            list = {
                ListBlock(
                    navControllerScreen = navControllerScreen,
                    windowSizeClass = windowSizeClass,
                    setIsDetailOpen = { isDetailOpen = it },
                    viewModel = viewModel
                )
            },
            detail = {
                DetailBlock(
                    navControllerScreen = navControllerScreen,
                    windowSizeClass = windowSizeClass,
                    setIsDetailOpen = { isDetailOpen = it },
                    viewModel = viewModel
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ListBlock(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val widthSizeClass by rememberUpdatedState(windowSizeClass.widthSizeClass)

    Surface {
        val scope = rememberCoroutineScope()

        val configuration = LocalConfiguration.current

        val searchState by viewModel.searchState.collectAsState()
        val suggestState by viewModel.suggestState.collectAsState()

        var status by remember {
            mutableStateOf(false)
        }

        Scaffold(
            topBar = {
                TopBar(
                    activeStatus = status,
                    suggestState = suggestState,
                    onArrowBack = { navControllerScreen.popBackStack() },
                    onQueryChange = {
                        scope.launch {
                            if (it.isNotEmpty()) {
                                status = true
                                viewModel.channel.send(MainEvent.GetSuggestSearch(it))
                            } else {
                                status = false
                            }
                        }
                    },
                    onSearch = {
                        scope.launch {
                            viewModel.channel.send(MainEvent.GetSearch(it))
                        }
                    },
                    onDismissRequest = { status = false }
                )
            },
            content = { pv ->
                Box(
                    Modifier
                        .padding(pv)
                        .fillMaxSize()
                ) {
                    when (searchState) {
                        is SearchState.Error -> Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = (searchState as SearchState.Error).e
                        )

                        is SearchState.Idle -> Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Idle"
                        )

                        is SearchState.Loading -> CircularProgressIndicator(
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )

                        is SearchState.Success -> {
                            val pagingItems =
                                (searchState as SearchState.Success).data.flow.collectAsLazyPagingItems()

                            LazyColumn(content = {
                                items(pagingItems) {
                                    when (it?.entityId) {
                                        "card-user" -> {
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(15.dp, 5.dp, 15.dp, 5.dp)
                                                    .clip(RoundedCornerShape(15.dp))
                                            ) {
                                                LazyRow {
                                                    it.entities.forEachIndexed { index, entity ->
                                                        item {
                                                            UserItem(
                                                                modifier = Modifier
                                                                    .padding(
                                                                        if (index == 0) 10.dp else 0.dp,
                                                                        10.dp,
                                                                        10.dp,
                                                                        10.dp
                                                                    ),
                                                                avatarUrl = entity.userAvatar,
                                                                userName = entity.username,
                                                                onClick = {
                                                                    scope.launch {
                                                                        viewModel.channel.send(
                                                                            MainEvent.GetSpace(
                                                                                entity.uid.toInt()
                                                                            )
                                                                        )
                                                                        navControllerScreen.navigate(
                                                                            ScreenManager.SpaceScreen.route
                                                                        )
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    }
                                                }

                                                Text(
                                                    modifier = Modifier.padding(
                                                        10.dp,
                                                        5.dp,
                                                        10.dp,
                                                        10.dp
                                                    ), text = "更多相关用户"
                                                )
                                            }
                                        }

                                        "card-feedTopic" -> {
                                            Card(
                                                modifier = Modifier
                                                    .padding(15.dp, 5.dp, 15.dp, 5.dp)
                                                    .clip(RoundedCornerShape(15.dp))
                                            ) {
                                                it.entities.forEachIndexed { index, entity ->
                                                    TopicItem(
                                                        modifier = Modifier
                                                            .padding(
                                                                10.dp,
                                                                if (index == 0) 10.dp else 0.dp,
                                                                10.dp,
                                                                10.dp
                                                            ),
                                                        topicUrl = entity.logo,
                                                        title = entity.title,
                                                        hotNum = entity.hotNumTxt,
                                                        feedNum = entity.feednum,
                                                        onTopic = {
                                                            scope.launch {
                                                                viewModel.channel.send(
                                                                    MainEvent.GetTopic(
                                                                        it
                                                                    )
                                                                )
                                                                navControllerScreen.navigate(
                                                                    ScreenManager.TopicScreen.route
                                                                )
                                                            }
                                                        }
                                                    )
                                                }

                                                Text(
                                                    modifier = Modifier.padding(
                                                        10.dp,
                                                        5.dp,
                                                        10.dp,
                                                        10.dp
                                                    ), text = "更多相关话题"
                                                )
                                            }
                                        }
                                    }

                                    if (it?.entityType == "feed") {
                                        val likeState by viewModel.likeState.collectAsState()

                                        val likeNum = if (likeState.likeModel.data == 0) it.likenum else likeState.likeModel.data

                                        /*var likeStatus by remember {
                                            *//*mutableStateOf(userAction?.let {
                                                userAction.like == 1
                                            } ?: false)*//*
                                            mutableStateOf( false)
                                        }*/

                                        val likeStatus = likeState.isLike

                                        FeedItem(
                                            modifier = Modifier.padding(15.dp, 5.dp, 15.dp, 5.dp),
                                            data = it,
                                            isNineGrid = false,
                                            likeNum = likeNum,
                                            likeStatus = likeStatus,
                                            onClick = {
                                                scope.launch {
                                                    viewModel.channel.send(MainEvent.GetDetails(it.id))
                                                    viewModel.channel.send(MainEvent.GetReply(it.id))
                                                    setIsDetailOpen(true)
                                                }
                                            },
                                            onLike = {
                                                scope.launch {
                                                    viewModel.channel.send(
                                                        if (likeStatus) MainEvent.Unlike(it.id) else MainEvent.Like(
                                                            it.id
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            })
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun DetailBlock(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val detailsState by viewModel.detailsState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(if (isSystemInDarkTheme()) 0xff0d0d0d else 0xfff5f5f5))
    ) {
        when (detailsState) {
            is DetailsState.Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = (detailsState as DetailsState.Error).e
                )
            }

            is DetailsState.Idle -> {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(100.dp),
                        painter = painterResource(id = R.drawable.coolapk),
                        contentDescription = "icon",
                        tint = Color(if (isSystemInDarkTheme()) 0xff161616 else 0xfff1f1f1)
                    )
                    Text(
                        text = "Coolbbs",
                        fontWeight = FontWeight.Bold,
                        fontSize = 55.sp,
                        color = Color(if (isSystemInDarkTheme()) 0xff161616 else 0xfff1f1f1),
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            is DetailsState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is DetailsState.Success -> {
                val detailsModel = (detailsState as DetailsState.Success).detailsEntity

                LaunchedEffect(key1 = detailsModel) {
                    viewModel.channel.send(MainEvent.GetReply(detailsModel.data.id))
                }

                DetailPager(
                    modifier = Modifier.fillMaxWidth(),
                    navControllerScreen = navControllerScreen,
                    detailsModel = detailsModel,
                    windowSizeClass = windowSizeClass,
                    setIsDetailOpen = setIsDetailOpen,
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    activeStatus: Boolean,
    onArrowBack: () -> Unit,
    onSearch: (String) -> Unit,
    suggestState: SuggestState,
    onQueryChange: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var query by remember {
        mutableStateOf("")
    }

    Column {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
            query = query,
            onQueryChange = {
                query = it
                onQueryChange(query)
            },
            onSearch = {
                onSearch(it)
            },
            active = false,
            onActiveChange = {},
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_tip),
                    color = Color.Gray
                )
            },
            leadingIcon = {
                IconButton(onClick = onArrowBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            content = {}
        )

        AnimatedVisibility(visible = activeStatus) {
            Popup(
                onDismissRequest = onDismissRequest
            ) {
                Card(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(start = 15.dp, end = 15.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        when (suggestState) {
                            is SuggestState.Error -> {
                                Text(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ), text = suggestState.e
                                )
                            }

                            SuggestState.Idle -> {
                                Text(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ), text = "Idle"
                                )
                            }

                            SuggestState.Loading -> CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )

                            is SuggestState.Success -> {
                                var dataList =
                                    suggestState.suggestSearchEntity.data

                                if (dataList.size >= 10) {
                                    dataList = dataList.subList(0, 9)
                                }

                                LazyColumn(content = {
                                    if (query.isNotEmpty()) {
                                        items(dataList) {
                                            if (
                                                it.entityType == "searchWord" &&
                                                it.url.indexOf("searchTab://apk?keyword=") == -1
                                            ) {
                                                Text(
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 15.dp,
                                                            end = 15.dp,
                                                            top = 10.dp,
                                                            bottom = 10.dp
                                                        )
                                                        .fillMaxWidth(),
                                                    text = it.title
                                                )
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserItem(
    modifier: Modifier = Modifier,
    avatarUrl: String,
    userName: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier.width(50.dp).clickableNoRipple {
        onClick()
    }) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray),
            model = ImageRequest.Builder(context)
                .crossfade(true)
                .data(avatarUrl)
                .build(),
            contentDescription = "user avatar"
        )
        Text(
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxWidth(),
            text = userName,
            fontSize = 11.sp,
            lineHeight = 15.sp,
            minLines = 2,
            maxLines = 2,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun TopicItem(
    modifier: Modifier = Modifier,
    topicUrl: String,
    title: String,
    hotNum: String,
    feedNum: String,
    onTopic: (String) -> Unit = {}
) {
    val context = LocalContext.current

    val imgLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val mPainter = rememberAsyncImagePainter(topicUrl, imgLoader)

    Row(modifier = modifier.height(50.dp).clickableNoRipple {
        onTopic(title)
    }) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray),
            painter = mPainter,
            contentDescription = "logo"
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 14.sp,
                maxLines = 1,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                text = "${hotNum}热度 ${feedNum}讨论",
                fontSize = 13.sp,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun FeedItem(modifier: Modifier = Modifier, avatarUrl: String, userName: String) {
    val context = LocalContext.current

    val imgLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val mPainter = rememberAsyncImagePainter(avatarUrl, imgLoader)

    Column(modifier = modifier.width(50.dp)) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray),
            model = ImageRequest.Builder(context)
                .crossfade(true)
                .data(avatarUrl)
                .build(),
            contentDescription = "user avatar"
        )
        Text(
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxWidth(),
            text = userName,
            fontSize = 11.sp,
            lineHeight = 15.sp,
            minLines = 2,
            maxLines = 2,
            textAlign = TextAlign.Center,
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
//    shareNum: Int = data.shareNum.toInt(),
    shareNum: Int = 0,
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

            Row(
                modifier = Modifier
                    .constrainAs(likeRef) {
                        start.linkTo(parent.start)
                        top.linkTo(
                            if (data.picArr.isNotEmpty()) {
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
//                            onReply(data.id)
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
private fun RailBar(
    onBack: () -> Unit
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        header = {
            val context = LocalContext.current

            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(0.15f),
                text = "Space",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(if (!isSystemInDarkTheme()) 0xff161616 else 0xfff1f1f1),
            )

            IconButton(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 30.dp)
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(7.dp)),
                onClick = onBack
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
        },
        content = {
        }
    )
}