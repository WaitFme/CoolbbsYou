package com.anpe.coolbbsyou.ui.host.innerScreen

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.anpe.coolbbsyou.data.remote.domain.topic.Data
import com.anpe.coolbbsyou.intent.state.topic.TopicState
import com.anpe.coolbbsyou.ui.main.MainViewModel

@Composable
fun TopicInnerScreen(
    navControllerInnerScreen: NavController,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val topicState by viewModel.topicState.collectAsState()

    when (topicState) {
        is TopicState.Error -> {}
        is TopicState.Idle -> {}
        is TopicState.Loading -> {}
        is TopicState.Success -> {
            val topicModel = (topicState as TopicState.Success).topicModel

            Content(
                data = topicModel.data,
                onBack = { navControllerInnerScreen.popBackStack() }
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