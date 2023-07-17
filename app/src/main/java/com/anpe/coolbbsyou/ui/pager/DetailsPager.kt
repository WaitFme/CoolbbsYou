package com.anpe.coolbbsyou.ui.pager

import android.text.TextUtils
import android.widget.TextView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.parseAsHtml
import androidx.core.text.toHtml
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.FullScreenImage
import com.anpe.coolbbsyou.ui.view.MyScaffold
import com.anpe.coolbbsyou.ui.view.NineImageGrid
import com.anpe.coolbbsyou.util.Utils.Companion.htmlToString
import com.anpe.coolbbsyou.util.Utils.Companion.richToString
import com.anpe.coolbbsyou.util.Utils.Companion.secondToDateString
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextThemeIntegration
import com.halilibo.richtext.ui.material3.Material3RichText

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailsPager(modifier: Modifier = Modifier, entity: DetailsEntity, viewModel: MainViewModel) {
    val context = LocalContext.current

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
                            .fillMaxWidth(0.7f)
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

@Composable
fun MyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            TextView(context).apply {
                this.text = text.richToString()
                this.textSize = fontSize.value
                this.maxLines = maxLines
                this.minLines = minLines
            }
        }
    )
}