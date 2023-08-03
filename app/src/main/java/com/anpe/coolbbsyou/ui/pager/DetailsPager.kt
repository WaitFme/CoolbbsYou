package com.anpe.coolbbsyou.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.bilibiliandyou.ui.view.TextDirection
import com.anpe.bilibiliandyou.ui.view.TextIcon
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.model.reply.Data
import com.anpe.coolbbsyou.data.state.ReplyState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.DialogImage
import com.anpe.coolbbsyou.ui.view.HtmlText
import com.anpe.coolbbsyou.ui.view.MyScaffold
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.RichTextUtil.Companion.R2T
import com.anpe.coolbbsyou.util.Utils.Companion.isTable
import com.anpe.coolbbsyou.util.Utils.Companion.timeStampInterval

@Composable
fun DetailsPager(modifier: Modifier = Modifier, entity: DetailsEntity, onBack: () -> Unit) {
    Surface {
        Row {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .alpha(0.5f)
                    .background(if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray)
            )
            MyScaffold(
                topBar = { TopBar(entity = entity, onBack = onBack) },
                content = {
                    Column(
                        modifier = modifier.padding(it)
                    ) {
                        Content(entity = entity)
                    }
                },
//                bottomBar = { BottomBar(entity = entity) }
            )
        }
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, entity: DetailsEntity) {
    val viewModel: MainViewModel = viewModel()

    LaunchedEffect(key1 = true, block = {
        viewModel.sendIntent(MainIntent.GetReply(entity.data.id))
    })

    val replyState by viewModel.replyState.collectAsState()

    var status by remember {
        mutableStateOf(false)
    }

    var initialPage by remember {
        mutableStateOf(0)
    }

    when (replyState) {
        is ReplyState.Error -> {}
        is ReplyState.Idle -> {}
        is ReplyState.Loading -> {}
        is ReplyState.Success -> {
            val pagingItems =
                (replyState as ReplyState.Success).pager.collectAsLazyPagingItems()

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                content = {
                item {
                    /*Text(
                        modifier = modifier
                            .padding(15.dp, 5.dp, 15.dp, 0.dp),
                        text = entity.data.message.R2T(),
                        fontSize = 16.sp,
                    )*/

                    HtmlText(
                        modifier = modifier
                            .padding(15.dp, 5.dp, 15.dp, 0.dp),
                        fontSize = 16.sp,
                        htmlText = entity.data.message, openLink = {
                            println(it)
                        }
                    )

                    if (entity.data.picArr.isNotEmpty()) {
                        NineImageGrid(
                            modifier = Modifier
                                .padding(13.dp, 10.dp, 13.dp, 0.dp)
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
                }

                items(pagingItems) {
                    it?.apply {
                        ReplyItem(data = this)
                    }
                }
            })
        }
    }

    if (status) {
        DialogImage(entity.data.picArr, initialPage, onDismissRequest = {
            status = false
        })
    }
}

@Composable
private fun ReplyItem(data: Data) {
    if (data.rusername.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 5.dp, 15.dp, 5.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            ConstraintLayout {
                val (avatarRef, usernameRef, messageRef) = createRefs()

                AsyncImage(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .constrainAs(avatarRef) {
                            start.linkTo(parent.start, 10.dp)
                            top.linkTo(parent.top, 10.dp)
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
                    text = data.username
                )

                Text(
                    modifier = Modifier
                        .constrainAs(messageRef) {
                            start.linkTo(avatarRef.end, 10.dp)
                            top.linkTo(usernameRef.bottom)
                            end.linkTo(parent.end, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                            width = Dimension.preferredWrapContent
                    },
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    text = data.message
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(modifier: Modifier = Modifier, entity: DetailsEntity, onBack: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (!configuration.isTable()) {
                IconButton(onClick = { onBack() }) {
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

@Composable
private fun BottomBar(modifier: Modifier = Modifier, entity: DetailsEntity) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextIcon(
            modifier = Modifier.weight(1f),
            text = entity.data.likenum.toString(),
            textDirection = TextDirection.End,
            iconId = R.drawable.baseline_thumb_up_alt_24,
            iconTint = MaterialTheme.colorScheme.surfaceTint
        )

        TextIcon(
            modifier = Modifier.weight(1f),
            text = entity.data.replynum.toString(),
            textDirection = TextDirection.End,
            iconId = R.drawable.baseline_chat_bubble_24,
            iconTint = MaterialTheme.colorScheme.surfaceTint
        )

        TextIcon(
            modifier = Modifier.weight(1f),
            textDirection = TextDirection.End,
            iconId = R.drawable.baseline_share_24,
            iconTint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}