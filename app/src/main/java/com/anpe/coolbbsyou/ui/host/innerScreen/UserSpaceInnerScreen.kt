package com.anpe.coolbbsyou.ui.host.innerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.data.remote.domain.space.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.feedList.FeedListState
import com.anpe.coolbbsyou.intent.state.space.SpaceState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.CustomProgressRect
import com.anpe.coolbbsyou.ui.view.FeedView
import com.anpe.coolbbsyou.ui.view.toFeedData
import com.anpe.coolbbsyou.util.Utils.Companion.numberAbbreviations
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval
import kotlinx.coroutines.launch

@Composable
fun UserSpaceInnerScreen(
    navControllerInnerScreen: NavController,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel,
) {
    val scope = rememberCoroutineScope()

    val spaceModel by viewModel.spaceState.collectAsState()
    val feedListState by viewModel.feedListState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
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

                    Content(
                        modifier = Modifier,
                        data = data,
                        feedListState = feedListState,
                        onBack = {
                            navControllerInnerScreen.popBackStack()
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
