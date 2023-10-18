package com.anpe.coolbbsyou.ui.host.screen

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.remote.domain.topic.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.DetailsState
import com.anpe.coolbbsyou.intent.state.topic.TopicState
import com.anpe.coolbbsyou.ui.view.DetailPager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.TwoPaneResponsiveLayout

@Composable
fun TopicScreen(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    viewModel: MainViewModel
) {
    var isDetailOpen by rememberSaveable { mutableStateOf(false) }

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

@Composable
private fun ListBlock(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val widthSizeClass by rememberUpdatedState(windowSizeClass.widthSizeClass)

    val topicState by viewModel.topicState.collectAsState()

    when (topicState) {
        is TopicState.Error -> {}
        is TopicState.Idle -> {}
        is TopicState.Loading -> {}
        is TopicState.Success -> {
            val topicModel = (topicState as TopicState.Success).topicModel

            Content(
                data = topicModel.data,
                onBack = { navControllerScreen.popBackStack() }
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    data: Data,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
    ) {
        data.run {
            val imgLoader = ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()

            val mPainter = rememberAsyncImagePainter(logo, imgLoader)

            Image(
                modifier = Modifier
                    .blur(30.dp)
                    .fillMaxWidth(),
                painter = mPainter,
                contentDescription = "logo"
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
                        .fillMaxSize()
                        .padding(15.dp)
                        .offset(y = (-55).dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        painter = mPainter,
                        contentDescription = "userAvatar"
                    )

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Row(modifier = Modifier.padding(top = 10.dp)) {
                        Text(
                            modifier = Modifier,
                            text = "热度 $hotNum | ",
                        )
                        Text(
                            modifier = Modifier,
                            text = "热度 $hotNumTxt | ",
                        )
                        Text(
                            modifier = Modifier,
                            text = "关注 $follownum ",
                        )
                        Text(
                            modifier = Modifier,
                            text = "讨论 $commentnum ",
                        )
                    }

                    if (description.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            text = description,
                        )
                    }
                }
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

@Composable
private fun RailBar(
    onBack: () -> Unit
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        header = {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(0.15f),
                text = "Topic",
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