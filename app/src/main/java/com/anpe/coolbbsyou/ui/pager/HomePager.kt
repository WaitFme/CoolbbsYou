package com.anpe.coolbbsyou.ui.pager

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.bilibiliandyou.ui.view.TextIcon
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.model.index.Data
import com.anpe.coolbbsyou.network.data.model.index.IndexEntity
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.network.data.state.IndexState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.screen.DetailsScreen
import com.anpe.coolbbsyou.ui.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import com.anpe.coolbbsyou.util.Utils.Companion.htmlToString
import kotlinx.coroutines.launch

@Composable
fun HomePager(navController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val indexModel: IndexEntity? = null
    var indexEntity by remember {
        mutableStateOf(indexModel)
    }

    var id by remember {
        mutableStateOf(-1)
    }

    // TEST
    LaunchedEffect(key1 = true, block = {
        viewModel.channel.send(MainIntent.GetIndex)

        viewModel.indexState.collect {
            when (it) {
                is IndexState.Error -> Toast.makeText(
                    context,
                    it.error,
                    Toast.LENGTH_SHORT
                ).show()

                IndexState.Idle -> {

                }

                IndexState.Loading -> {

                }

                is IndexState.Success -> indexEntity = it.indexEntity
            }
        }
    })

    LazyColumn(
        contentPadding = PaddingValues(15.dp, 0.dp, 15.dp, 10.dp)
    ) {
        indexEntity?.apply {
            val list = this.data.subList(3, this.data.size - 1)

            items(list) {
                Item(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    data = it, onClick = {
                    id = it.id
                    scope.launch {
                        if (configuration.screenWidthDp < 800) {
                            navController.navigate("${ScreenManager.DetailsScreen.route}/$id")
                        } else {
                            viewModel.channel.send(MainIntent.GetDetails(id))
                        }
                    }
                })
            }
        }
    }
}

@Composable
private fun Item(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .clickableNoRipple {
                onClick()
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)
    ) {
        ConstraintLayout {
            val (
                proPicRef,
                nameRef,
                messageRef,
                infoHtmlRef,
                deviceRef,
                deviceLabelRef,
                picRef,
                likeRef,
                replyRef
            ) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(proPicRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
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
                    .padding(top = 10.dp)
                    .constrainAs(nameRef) {
                        start.linkTo(proPicRef.end)
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
                text = data.infoHtml.htmlToString(),
                fontSize = 13.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .constrainAs(deviceRef) {
                        start.linkTo(infoHtmlRef.end)
                        top.linkTo(nameRef.bottom)
                    },
                text = data.deviceTitle,
                fontSize = 13.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 10.dp)
                    .constrainAs(messageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(proPicRef.bottom)
                        end.linkTo(parent.end)
                        this.width = Dimension.matchParent
                    },
                text = data.message.htmlToString()
            )

            if (data.picArr.isNotEmpty()) {
                NineImageGrid(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .constrainAs(picRef) {
                            start.linkTo(parent.start)
                            top.linkTo(messageRef.bottom)
                            end.linkTo(parent.end)
                            width = Dimension.matchParent
                        },
                    list = data.picArr
                )
            }

            Row(
                modifier = Modifier
                .padding(15.dp, 10.dp, 15.dp, 10.dp)
                .constrainAs(likeRef) {
                    start.linkTo(parent.start)
                    top.linkTo(if (data.picArr.isNotEmpty()) picRef.bottom else messageRef.bottom)
                    end.linkTo(parent.end)
                }
            ) {
                TextIcon(
                    modifier = Modifier.weight(1f),
                    text = data.likenum.toString(),
                    iconId = R.drawable.baseline_thumb_up_alt_24,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    text = data.replynum.toString(),
                    iconId = R.drawable.baseline_chat_bubble_24,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
                TextIcon(
                    modifier = Modifier.weight(1f),
                    iconId = R.drawable.baseline_share_24,
                    iconTint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    }
}