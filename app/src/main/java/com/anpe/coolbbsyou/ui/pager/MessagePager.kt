package com.anpe.coolbbsyou.ui.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.state.NotificationState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.util.Utils.Companion.richToString
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval

@Composable
fun MessagePager(viewModel: MainViewModel) {
    val notificationState by viewModel.notificationState.collectAsState()

    val timeMillis = System.currentTimeMillis()

    LaunchedEffect(key1 = true, block = {
        viewModel.sendIntent(MainIntent.GetNotification)
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (notificationState) {
            is NotificationState.Error -> {
                Text(modifier = Modifier.align(Alignment.Center), text = (notificationState as NotificationState.Error).e)
            }
            NotificationState.Idle -> {
                Text(modifier = Modifier.align(Alignment.Center), text = "加载失败！")
            }
            NotificationState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is NotificationState.Success -> {
                val dataList = (notificationState as NotificationState.Success).pager.flow.collectAsLazyPagingItems()

                LazyColumn(content = {
                    item {
                        Column {
                            BlockItem(title = "我的动态")
                            BlockItem(title = "我的评论")
                            BlockItem(title = "我收到的赞")
                            BlockItem(title = "好友关注")
                            BlockItem(title = "私信")
                        }
                    }

                    items(dataList) {
                        it?.apply {
                            NotificationItem(
                                fromUserAvatar,
                                fromusername,
                                note,
                                dateline.timeStampInterval(timeMillis)
                            )
                        }
                    }
                })
            }
        }
    }
}

@Composable
private fun BlockItem(
    modifier: Modifier = Modifier,
    title: String,
    tip: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(30.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            tip?.apply {
                Text(text = this, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun NotificationItem(userAvatar: String, username: String, node: String, time: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp, 15.dp, 10.dp)
    ) {
        val (avatarRef, nameRef, messageRef, timeRef) = createRefs()

        AsyncImage(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .constrainAs(avatarRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            model = userAvatar,
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(nameRef) {
                    start.linkTo(avatarRef.end, 10.dp)
                    top.linkTo(avatarRef.top)
                },
            text = username
        )

        Text(
            modifier = Modifier
                .constrainAs(messageRef) {
                    start.linkTo(nameRef.start)
                    top.linkTo(nameRef.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            text = node.richToString(),
            fontSize = 13.sp,
            lineHeight = 17.sp
        )

        Text(
            modifier = Modifier
                .constrainAs(timeRef) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            text = time,
            fontSize = 11.sp
        )
    }
}
