package com.anpe.coolbbsyou.ui.host.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.DetailsState
import com.anpe.coolbbsyou.intent.state.feedList.FeedListState
import com.anpe.coolbbsyou.intent.state.space.SpaceState
import com.anpe.coolbbsyou.net.model.space.Data
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.CustomProgressRect
import com.anpe.coolbbsyou.ui.view.DetailPager
import com.anpe.coolbbsyou.ui.view.FeedView
import com.anpe.coolbbsyou.ui.view.TwoPaneResponsiveLayout
import com.anpe.coolbbsyou.ui.view.toFeedData
import com.anpe.coolbbsyou.util.Utils.Companion.numberAbbreviations
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval
import kotlinx.coroutines.launch

@Composable
fun SpaceScreen(
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

@Composable
private fun ListBlock(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val widthSizeClass by rememberUpdatedState(windowSizeClass.widthSizeClass)

    val spaceModel by viewModel.spaceState.collectAsState()
    val feedListState by viewModel.feedListState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (spaceModel) {
            is SpaceState.Idle -> {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(15.dp),
                    text = "IDLE"
                )
            }

            is SpaceState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is SpaceState.Success -> {
                val data = (spaceModel as SpaceState.Success).spaceModel.data

                LaunchedEffect(key1 = Unit) {
                    viewModel.channel.send(MainEvent.GetFeedList(data.uid, 1))
                }

                ContentTest(
                    modifier = Modifier,
                    windowSizeClass = windowSizeClass,
                    data = data,
                    feedListState = feedListState,
                    onBack = {
                        navControllerScreen.popBackStack()
                    },
                    onClickFeed = {
                        setIsDetailOpen(true)
                        scope.launch {
                            viewModel.channel.send(MainEvent.GetDetails(it))
                            viewModel.channel.send(MainEvent.GetReply(it))
                        }
                    }
                )
            }

            is SpaceState.Error -> {
                val error = (spaceModel as SpaceState.Error).e

                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(15.dp),
                    text = error
                )
            }
        }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Content(
    modifier: Modifier,
    data: Data,
    feedListState: FeedListState,
    onBack: () -> Unit = {},
    onClickFeed: (Int) -> Unit = {}
) = data.run {
    val context = LocalContext.current

    val verifyColor: Color = when (verifyIcon) {
        "v_yellow" -> Color.Yellow
        "v_green" -> Color.Green
        "v_blue" -> Color.Blue
        else -> Color.Unspecified
    }

    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = ImageRequest.Builder(context)
                .data(cover)
                .crossfade(true)
                .build(),
            contentDescription = "cover"
        )

        IconButton(
            modifier = Modifier
                .alpha(0.75f)
                .systemBarsPadding()
                .padding(15.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape),
            onClick = onBack,
            content = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        )

        Column(
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 15.dp, 15.dp, 10.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = username,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        modifier = Modifier,
                        text = "关注 $follow | ",
                        fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier,
                        text = "粉丝 $fans",
                        fontSize = 14.sp
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "LV$level $experience/$nextLevelExperience",
                    fontSize = 13.sp
                )

                CustomProgressRect(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(top = 5.dp),
                    currentValue = experience,
                    maxValue = nextLevelExperience,
                    primaryColor = MaterialTheme.colorScheme.primary,
                    secondaryColor = MaterialTheme.colorScheme.primaryContainer
                )

                if (bio.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "简介：$bio",
                        fontSize = 14.sp
                    )
                }

                FlowRow(modifier = Modifier.padding(top = 10.dp)) {
                    TextLabel(
                        modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                        text = "获赞：${beLikeNum.numberAbbreviations()}"
                    )
                    TextLabel(
                        modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                        text = "动态：$feed"
                    )
                    TextLabel(
                        modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                        text = "最近活跃：${(logintime.toLong() * 1000).timeStampInterval(System.currentTimeMillis())}"
                    )
                    if (province.isNotEmpty()) {
                        TextLabel(
                            modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                            text = "$province $city"
                        )
                    }
                    if (verifyStatus == 1) {
                        TextLabel(
                            modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                            text = verifyTitle
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (feedListState) {
                    is FeedListState.Idle -> {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(15.dp),
                            text = "IDLE"
                        )
                    }

                    is FeedListState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is FeedListState.Success -> {
                        val dataList = feedListState.feedListModel.data

                        LazyColumn(contentPadding = PaddingValues(15.dp, 10.dp, 15.dp, 10.dp)) {
                            items(dataList) {
                                FeedView(
                                    modifier = Modifier.padding(bottom = 10.dp),
                                    data = it.toFeedData(),
                                    isNineGrid = false,
                                    likeStatus = false,
                                    onClick = {
                                        onClickFeed(it.id)
                                    },
                                    onAvatar = {

                                    },
                                    onClickPic = {

                                    },
                                    onTopic = {

                                    }
                                )
                            }
                        }
                    }

                    is FeedListState.Error -> {
                        val error = (feedListState as SpaceState.Error).e

                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(15.dp),
                            text = error
                        )
                    }
                }
            }
        }

        Box(
            Modifier
                .align(Alignment.TopEnd)
                .padding(top = 160.dp, end = 15.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(context)
                    .data(userAvatar)
                    .crossfade(true)
                    .build(),
                contentDescription = "userAvatar"
            )
            Spacer(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
                    .offset(28.dp, 28.dp)
                    .background(verifyColor, CircleShape)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ContentTest(
    modifier: Modifier,
    windowSizeClass: WindowSizeClass,
    data: Data,
    feedListState: FeedListState,
    onBack: () -> Unit = {},
    onClickFeed: (Int) -> Unit = {}
) = data.run {
    val context = LocalContext.current

    val lazyListState = rememberLazyListState()

    var firstItemHeight by remember {
        mutableIntStateOf(1)
    }

    val verifyColor: Color = when (verifyIcon) {
        "v_yellow" -> Color.Yellow
        "v_green" -> Color.Green
        "v_blue" -> Color.Blue
        else -> Color.Unspecified
    }

    if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded) {
        IconButton(
            modifier = Modifier
                .alpha(0.75f)
                .systemBarsPadding()
                .padding(15.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape),
            onClick = onBack,
            content = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(top = 0.dp, bottom = 15.dp),
        state = lazyListState
    ) {
        item {
            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(context)
                    .data(cover)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "cover"
            )
        }

        item {
            Box(
                modifier = Modifier.onGloballyPositioned {
                    firstItemHeight = it.size.height
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(15.dp, 15.dp, 15.dp, 10.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = username,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(modifier = Modifier.padding(top = 10.dp)) {
                        Text(
                            modifier = Modifier,
                            text = "关注 $follow | ",
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier,
                            text = "粉丝 $fans",
                            fontSize = 14.sp
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "LV$level $experience/$nextLevelExperience",
                        fontSize = 13.sp
                    )

                    CustomProgressRect(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(top = 5.dp),
                        currentValue = experience,
                        maxValue = nextLevelExperience,
                        primaryColor = MaterialTheme.colorScheme.primary,
                        secondaryColor = MaterialTheme.colorScheme.primaryContainer
                    )

                    if (bio.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            text = "简介：$bio",
                            fontSize = 14.sp
                        )
                    }

                    FlowRow(modifier = Modifier.padding(top = 10.dp)) {
                        TextLabel(
                            modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                            text = "获赞：${beLikeNum.numberAbbreviations()}"
                        )
                        TextLabel(
                            modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                            text = "动态：$feed"
                        )
                        TextLabel(
                            modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                            text = "最近活跃：${
                                (logintime.toLong() * 1000).timeStampInterval(
                                    System.currentTimeMillis()
                                )
                            }"
                        )
                        if (province.isNotEmpty()) {
                            TextLabel(
                                modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                                text = "$province $city"
                            )
                        }
                        if (verifyStatus == 1) {
                            TextLabel(
                                modifier = Modifier.padding(end = 5.dp, bottom = 5.dp),
                                text = verifyTitle
                            )
                        }
                    }
                }


                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 15.dp)
                        .offset(y = (-40).dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        model = ImageRequest.Builder(context)
                            .data(userAvatar)
                            .crossfade(true)
                            .build(),
                        contentDescription = "userAvatar"
                    )
                    Spacer(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                            .offset(28.dp, 28.dp)
                            .background(verifyColor, CircleShape)
                    )
                }
            }
        }

        if (feedListState is FeedListState.Success) {
            val dataList = feedListState.feedListModel.data

            items(dataList) {
                FeedView(
                    modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 5.dp),
                    data = it.toFeedData(),
                    isNineGrid = false,
                    likeStatus = false,
                    onClick = {
                        onClickFeed(it.id)
                    },
                    onAvatar = {

                    },
                    onClickPic = {

                    },
                    onTopic = {

                    }
                )
            }
        }
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

@Composable
private fun TextLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    if (text.isNotEmpty()) {
        Text(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(5.dp))
                .padding(start = 3.dp, top = 1.dp, end = 3.dp, bottom = 1.dp),
            text = text,
            fontSize = 12.sp
        )
    }
}