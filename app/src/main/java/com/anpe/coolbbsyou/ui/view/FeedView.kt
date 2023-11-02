package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.bilibiliandyou.ui.view.TextDirection
import com.anpe.bilibiliandyou.ui.view.TextIcon
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.remote.domain.index.Data
import com.anpe.coolbbsyou.intent.state.global.ImageArrayType
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToast
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple

data class FeedData(
    val uid: Int,
    val feedId: Int,
    val userAvatar: String,
    val userName: String,
    val feedInfo: String,
    val feedDevice: String,
    val feedMessage: String,
    val feedPicArray: List<String>,
    val hotReply: String?,
    val likeNum: Int,
    val replyNum: Int,
    val shareNum: Int,
)

fun com.anpe.coolbbsyou.data.remote.domain.index.Data.toFeedData() = FeedData(
    uid = uid,
    feedId = id,
    userAvatar = userAvatar,
    userName = username,
    feedInfo = infoHtml,
    feedDevice = deviceTitle,
    feedMessage = message,
    feedPicArray = picArr,
    hotReply = replyRows.toString(),
    likeNum = likenum,
    replyNum = replynum,
    shareNum = shareNum,
)

fun com.anpe.coolbbsyou.data.remote.domain.search.Data.toFeedData() = FeedData(
    uid = uid.toInt(),
    feedId = id,
    userAvatar = userAvatar,
    userName = username,
    feedInfo = infoHtml,
    feedDevice = deviceTitle,
    feedMessage = message,
    feedPicArray = picArr,
    hotReply = null,
    likeNum = likenum,
    replyNum = replynum,
    shareNum = -1,
)

fun com.anpe.coolbbsyou.data.remote.domain.feedList.Data.toFeedData() = FeedData(
    uid = uid,
    feedId = id,
    userAvatar = userAvatar,
    userName = username,
    feedInfo = infoHtml,
    feedDevice = deviceTitle,
    feedMessage = message,
    feedPicArray = picArr,
    hotReply = null,
    likeNum = likenum,
    replyNum = replynum,
    shareNum = -1,
)

@Composable
fun FeedView(
    modifier: Modifier = Modifier,
    data: FeedData,
    isNineGrid: Boolean,
    likeStatus: Boolean,
    onClick: () -> Unit,
    onAvatar: (Int) -> Unit,
    onClickPic: (Int) -> Unit,
    onTopic: (String) -> Unit,
    onLike: (Boolean) -> Unit = {},
    onReply: (Int) -> Unit = {},
    onShare: (Int) -> Unit = {},
    onFunction: (String) -> Unit = {}
) {
    val context = LocalContext.current

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
                text = data.userName,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Row(
                modifier = Modifier.constrainAs(infoHtmlRef) {
                    start.linkTo(nameRef.start)
                    top.linkTo(nameRef.bottom)
                },
            ) {
                HtmlText(
                    modifier = Modifier,
                    htmlText = data.feedInfo,
                    fontSize = 11.sp,
                    openLink = {}
                )

                HtmlText(
                    modifier = Modifier,
                    htmlText = data.feedDevice,
                    fontSize = 11.sp,
                    openLink = {}
                )
            }

            HtmlText(
                modifier = Modifier
                    .constrainAs(messageRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(proPicRef.bottom, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        this.width = Dimension.matchParent
                    },
                htmlText = data.feedMessage,
                fontSize = 9.sp,
                openLink = {
                    it.showToast()
                }
            )

            if (data.feedPicArray.isNotEmpty()) {
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
                            for ((num, pic) in data.feedPicArray.withIndex()) {
                                item {
                                    AsyncImage(
                                        modifier = Modifier
                                            .clickableNoRipple {
                                                onClickPic(num)
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
                        list = data.feedPicArray,
                        onClick = {
                            onClickPic(it)
                        }
                    )
                }
            }

            if (!data.hotReply.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .constrainAs(hotReplyRef) {
                            start.linkTo(parent.start)
                            top.linkTo(
                                if (data.feedPicArray.isNotEmpty()) picRef.bottom else messageRef.bottom,
                                10.dp
                            )
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = data.hotReply,
                        maxLines = 3,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(likeRef) {
                        start.linkTo(parent.start)
                        top.linkTo(
                            if (data.feedPicArray.isNotEmpty()) {
                                picRef.bottom
                            } else if (!data.hotReply.isNullOrEmpty()) {
                                hotReplyRef.bottom
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
                            onLike(likeStatus)
                        },
                    text = data.likeNum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_thumb_up_alt_24,
                    iconTint = if (likeStatus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
                TextIcon(
                    modifier = Modifier
                        .weight(1f)
                        .clickableNoRipple {
                            onReply(data.feedId)
                        },
                    text = data.replyNum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_chat_bubble_24,
                    iconTint = MaterialTheme.colorScheme.secondary
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    iconId = R.drawable.baseline_share_24,
                    text = data.shareNum.let { if (it == 0) "Share" else it.toString() },
                    textDirection = TextDirection.Bottom,
                    iconTint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun FeedView(
    modifier: Modifier = Modifier,
    data: com.anpe.coolbbsyou.data.remote.domain.index.Data,
    isNineGrid: Boolean,
    likeStatus: Boolean,
    onClick: () -> Unit,
    onAvatar: (Int) -> Unit,
    onClickPic: (Int) -> Unit,
    onTopic: (String) -> Unit,
    onLike: (Boolean) -> Unit = {},
    onReply: (Int) -> Unit = {},
    onShare: (Int) -> Unit = {},
    onFunction: (String) -> Unit = {}
) {
    FeedView(
        modifier = modifier,
        data = data.toFeedData(),
        isNineGrid = isNineGrid,
        likeStatus = likeStatus,
        onClick = onClick,
        onAvatar = onAvatar,
        onLike = onLike,
        onReply = onReply,
        onShare = onShare,
        onClickPic = onClickPic,
        onFunction = onFunction,
        onTopic = onTopic
    )
}

@Composable
fun FeedItem(
    modifier: Modifier = Modifier,
    data: Data,
    imageType: ImageArrayType,
    likeNum: Int = data.likenum,
    likeStatus: Boolean,
    replyNum: Int = data.replynum,
    shareNum: Int = data.shareNum,
    onClick: () -> Unit,
    onAvatar: (Int) -> Unit,
    onClickPic: (Int) -> Unit,
    onTopic: (String) -> Unit,
    onLike: () -> Unit = {},
    onReply: (Int) -> Unit = {},
    onShare: (Int) -> Unit = {},
    onFunction: (String) -> Unit = {}
) {
    val context = LocalContext.current

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
                avatarRef,
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
                    .clickableNoRipple {
                        onAvatar(data.uid)
                    }
                    .constrainAs(avatarRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                    },
                model = ImageRequest.Builder(context)
                    .data(data.userAvatar)
                    .size(100)
                    .crossfade(true)
                    .build(),
                contentDescription = "avatar"
            )

            Text(
                modifier = Modifier
                    .constrainAs(nameRef) {
                        start.linkTo(avatarRef.end, 10.dp)
                        top.linkTo(avatarRef.top)
                    },
                text = data.username,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            HtmlText(
                modifier = Modifier.constrainAs(infoHtmlRef) {
                    start.linkTo(nameRef.start)
                    top.linkTo(nameRef.bottom)
                },
                htmlText = data.infoHtml,
                fontSize = 11.sp,
                openLink = {}
            )

            HtmlText(
                modifier = Modifier
                    .constrainAs(deviceRef) {
                        start.linkTo(infoHtmlRef.end, 5.dp)
                        top.linkTo(infoHtmlRef.top)
                    },
                htmlText = data.deviceTitle,
                fontSize = 11.sp,
                openLink = {}
            )

            HtmlText(
                modifier = Modifier
                    .constrainAs(messageRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(avatarRef.bottom, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        this.width = Dimension.matchParent
                    },
                htmlText = data.message,
                openLink = {
                    it.showToast()
                    onTopic(it)
                }
            )

            if (data.picArr.isNotEmpty()) {
                when (imageType) {
                    ImageArrayType.NineGrid -> {
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
                                onClickPic(it)
                            }
                        )
                    }

                    ImageArrayType.ImageRow -> {
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
                                                    onClickPic(num)
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
                            }
                        )
                    }
                }
            }

            if (data.replyRows.isNotEmpty()) {
                val replyRow = data.replyRows[0]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .constrainAs(hotReplyRef) {
                            start.linkTo(parent.start)
                            top.linkTo(
                                if (data.picArr.isNotEmpty()) picRef.bottom else messageRef.bottom,
                                10.dp
                            )
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "${replyRow.username}: ${replyRow.message}",
                        maxLines = 3,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(likeRef) {
                        start.linkTo(parent.start)
                        top.linkTo(
                            if (data.replyRows.isNotEmpty()) {
                                hotReplyRef.bottom
                            } else if (data.picArr.isNotEmpty()) {
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
                            onLike()
                        },
                    text = likeNum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_thumb_up_alt_24,
                    iconTint = if (likeStatus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
                TextIcon(
                    modifier = Modifier
                        .weight(1f)
                        .clickableNoRipple {
                            onReply(data.id)
                        },
                    text = replyNum.toString(),
                    textDirection = TextDirection.Bottom,
                    iconId = R.drawable.baseline_chat_bubble_24,
                    iconTint = MaterialTheme.colorScheme.secondary
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    iconId = R.drawable.baseline_share_24,
                    text = shareNum.let { if (it == 0) "Share" else it.toString() },
                    textDirection = TextDirection.Bottom,
                    iconTint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}