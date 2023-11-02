package com.anpe.coolbbsyou.ui.host.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.data.remote.domain.index.Data
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.IndexState
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.ArticleItem
import com.anpe.coolbbsyou.ui.view.FeedItem
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePager(
    navControllerScreen: NavHostController,
    navControllerPager: NavHostController,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val indexState by viewModel.indexState.collectAsState()

    val globalState by viewModel.globalState.collectAsState()

    val lazyPagingItems = (indexState as IndexState.Success).pager.collectAsLazyPagingItems()

    var refreshing by remember {
        mutableStateOf(false)
    }

    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            lazyPagingItems.refresh()
            refreshing = false
        }
    })

    Box(Modifier.fillMaxSize()) {
        when (indexState) {
            is IndexState.Error -> {
                val error = (indexState as IndexState.Error).error
                Text(modifier = Modifier.align(Alignment.Center), text = error)
            }

            is IndexState.Idle -> {
                Text(modifier = Modifier.align(Alignment.Center), text = "idle")
            }

            is IndexState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IndexState.Success -> {
                LazyColumn(
                    modifier = Modifier.pullRefresh(refreshState),
                    contentPadding = PaddingValues(15.dp, 0.dp, 15.dp, 10.dp),
                ) {
                    items(lazyPagingItems) {data ->
                        when (data.entityType) {
                            "imageCarouselCard_1" -> {
                                BannerItem(
                                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                                    data = data,
                                    onClick = {
                                        scope.launch {
                                            val url = data.entities[0].url
                                            val substring =
                                                url.substring(url.indexOf("url=") + 4)
                                            viewModel.channel.send(
                                                MainEvent.GetTodayCool(
                                                    url = substring,
                                                    page = 1
                                                )
                                            )
                                            navControllerScreen.navigate(ScreenManager.NewsScreen.route)
                                        }
                                    },
                                )
                            }

                            "feed" -> {
                                FeedItem(
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                    data = data,
                                    imageType = globalState.imageArrayType,
                                    likeNum = data.likenum,
                                    likeStatus = data?.userAction?.like == 1,
                                    onClick = {
                                        scope.launch {
                                            viewModel.channel.send(MainEvent.GetDetails(data.id)) // 48449942
                                            viewModel.channel.send(MainEvent.GetReply(data.id))
                                            setIsDetailOpen(true)
                                        }
                                    },
                                    onLike = {
                                        /*scope.launch {
                                            viewModel.channel.send(
                                                if (it) MainEvent.Unlike(data.id) else MainEvent.Like(data.id)
                                            )
                                        }*/
                                    },
                                    onClickPic = {
                                        viewModel.showImage(it, data.picArr, navControllerScreen)
                                    },
                                    onAvatar = {
                                        scope.launch {
                                            viewModel.channel.send(MainEvent.GetSpace(it))
                                            navControllerScreen.navigate(ScreenManager.SpaceScreen.route)
                                        }
                                    },
                                    onTopic = {
                                        scope.launch {
                                            viewModel.channel.send(MainEvent.GetTopic(it))
                                            navControllerScreen.navigate(ScreenManager.TopicScreen.route)
                                        }
                                    }
                                )
                            }

                            "imageTextScrollCard" -> {
                                ArticleItem(
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                                    data = data,
                                    onClick = {
                                    }
                                )
                            }
                        }
                    }
                }

                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = refreshing,
                    state = refreshState,
                    contentColor = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BannerItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    val list = mutableListOf<String>()

    data.entities.forEach {
        when (it.title) {
            "今日酷安" -> {
                list.add(it.pic)
            }
            "新机资讯" -> {
                list.add(it.pic)
            }
        }
    }

    if (list.isNotEmpty()) {
        val state = rememberPagerState(initialPage = 0) {
            list.size
        }

        HorizontalPager(
            modifier = modifier
                .padding(bottom = 5.dp)
                .clip(RoundedCornerShape(15.dp)),
            state = state,
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .clickableNoRipple {
                        onClick()
                    },
                model = ImageRequest.Builder(context)
                    .data(list[it])
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "image"
            )
        }
    }
}

private fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>?,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyItemScope.(value: T) -> Unit
) {
    items?.run {
        items(
            count = itemCount,
            key = null
        ) { index ->
            if (this@run[index] != null) {
                itemContent(this@run[index]!!)
            }
        }
    }
}
