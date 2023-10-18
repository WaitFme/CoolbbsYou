package com.anpe.coolbbsyou.ui.host.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.remote.domain.today.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.DetailsState
import com.anpe.coolbbsyou.intent.state.TodayState
import com.anpe.coolbbsyou.ui.view.DetailPager
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.HtmlText
import com.anpe.coolbbsyou.ui.view.TwoPaneResponsiveLayout
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval
import kotlinx.coroutines.launch

@Composable
fun NewsScreen(
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

    val configuration = LocalConfiguration.current

    val todayState by viewModel.todayState.collectAsState()

    var refreshing by remember {
        mutableStateOf(false)
    }
    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            /*url?.apply {
                viewModel.channel.send(MainIntent.GetTodayCool(url = this, page = 1))
            }*/
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    androidx.compose.material.IconButton(onClick = {
                        navControllerScreen.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = ScreenManager.NewsScreen.resourceId))
                }
            )
        }
    ) { pv->
        Box(
            Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            val list: List<Data> = listOf()
            var dataList by remember {
                mutableStateOf(list)
            }

            when (todayState) {
                is TodayState.Error -> {
                    refreshing = false
                    val error = (todayState as TodayState.Error).e
                    Text(modifier = Modifier.align(Alignment.Center), text = error)
                }

                TodayState.Idle -> {
                    refreshing = false
                    Text(modifier = Modifier.align(Alignment.Center), text = "idle")
                }

                TodayState.Loading -> {
                    if (dataList.isEmpty()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is TodayState.Success -> {
                    refreshing = false
                    dataList = (todayState as TodayState.Success).todayCoolEntity.data
                }
            }

            LazyColumn(
                modifier = Modifier
                    .pullRefresh(refreshState)
                    .fillMaxHeight(),
                contentPadding = PaddingValues(15.dp, 0.dp, 15.dp, 10.dp),
                content = {
                    items(items = dataList) {
                        when (it.entityType) {
                            "feed" -> {
                                FeedItem(
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                    data = it,
                                    onClick = {
                                        scope.launch {
                                            viewModel.channel.send(MainEvent.GetDetails(it.id))
                                            setIsDetailOpen(true)
                                        }
                                    }
                                )
                            }

                            "imageTextGridCard" -> {
                                ImageTextItem(
                                    modifier = Modifier.padding(
                                        top = 5.dp,
                                        bottom = 5.dp
                                    ), data = it, onClick = {

                                    }
                                )
                            }
                        }
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
}

@Composable
private fun FeedItem(
    modifier: Modifier = Modifier,
    data: Data,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    val timeMillis = System.currentTimeMillis()

    Card(
        modifier = modifier
            .clickableNoRipple {
                onClick()
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)
    ) {
        HtmlText(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp),
            htmlText = data.message,
            openLink = {}
        )

        if (data.picArr.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                content = {
                    for ((index, string) in data.picArr.withIndex()) {
                        item {
                            AsyncImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(
                                        start = if (index == 0) 0.dp else 2.dp,
                                        end = if (index == data.picArr.size - 1) 0.dp else 2.dp
                                    )
                                    .clip(RoundedCornerShape(10.dp))
                                    .aspectRatio(1f),
                                model = ImageRequest.Builder(context)
                                    .data(string)
                                    .size(500)
                                    .build(),
                                contentScale = ContentScale.Crop,
                                contentDescription = "image"
                            )
                        }
                    }
                }
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
            text = "${data.username} ${data.replynum}评论 ${
                (data.createTime.toLong() * 1000).timeStampInterval(
                    timeMillis
                )
            }",
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ImageTextItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        LazyRow(content = {
            items(data.entities) {
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .padding(
                            end = 10.dp
                        )
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
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(5.dp),
                        text = it.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        minLines = 2,
                        fontSize = 15.sp
                    )
                }
            }
        })
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