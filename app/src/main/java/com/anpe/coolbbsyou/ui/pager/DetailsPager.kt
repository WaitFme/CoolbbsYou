package com.anpe.coolbbsyou.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.ui.view.MyScaffold
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.secondToDateString
import com.halilibo.richtext.ui.material3.Material3RichText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPager(modifier: Modifier = Modifier, entity: DetailsEntity) {
    val context = LocalContext.current

    Surface {

        Row {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .alpha(0.5f)
                    .background(Color.LightGray)
            )
            MyScaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(text = entity.data.username)
                                Text(
                                    text = entity.data.deviceTitle,
                                    fontSize = 13.sp
                                )
                            }
                        },
                        actions = {
                            Button(
                                modifier = Modifier
                                    .padding(end = 12.dp),
                                contentPadding = PaddingValues(2.dp),
                                onClick = { /*TODO*/ }
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .size(35.dp)
                                        .clip(CircleShape),
                                    model = ImageRequest.Builder(context)
                                        .data(entity.data.userAvatar)
                                        .size(100)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = ""
                                )

                                Text(modifier = Modifier.padding(end = 10.dp), text = "订阅")
                            }
                        }
                    )
                }
            ) {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(it)
                ) {
                    Material3RichText {
                        Text(
                            modifier = Modifier
                                .padding(15.dp, 5.dp, 15.dp, 0.dp),
                            text = entity.data.message.parseAsHtml().toString(),
                            fontSize = 20.sp
                        )
                    }

                    /*HorizontalPager(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .height(250.dp),
                        pageCount = entity.data.picArr.size
                    ) {
                        AsyncImage(model = entity.data.picArr[it], contentDescription = "")
                    }*/

                    if (entity.data.picArr.isNotEmpty()) {
                        NineImageGrid(
                            modifier = Modifier
                                .padding(13.dp, 10.dp, 13.dp, 0.dp)
                                .width(500.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            list = entity.data.picArr,
                            itemPadding = PaddingValues(2.dp),
                            itemClip = RoundedCornerShape(5.dp)
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(15.dp, 10.dp, 15.dp, 0.dp),
                        text = entity.data.createTime.secondToDateString()
                    )
                }
            }
        }
    }
}