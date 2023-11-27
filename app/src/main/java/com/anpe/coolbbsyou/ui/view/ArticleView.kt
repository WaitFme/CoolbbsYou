package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.net.model.today.Data
import com.anpe.coolbbsyou.net.model.today.Entity
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple

@Composable
fun ArticleView(
    modifier: Modifier = Modifier,
    data: Data,
    openLink: (String) -> Unit = {},
    onClick: () -> Unit = {},
) {
    ArticleView(
        modifier,
        data.toArticles(),
        openLink,
        onClick
    )
}

@Composable
fun ArticleView(
    modifier: Modifier = Modifier,
    data: com.anpe.coolbbsyou.net.model.index.Data,
    openLink: (String) -> Unit = {},
    onClick: () -> Unit = {},
) {
    ArticleView(
        modifier,
        data.toArticles(),
        openLink,
        onClick
    )
}

@Composable
fun ArticleView(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    openLink: (String) -> Unit = {},
    onClick: () -> Unit,
) {
    LazyRow(
        modifier = modifier,
        content = {
            articles.forEachIndexed { index, article ->
                item(index) {
                    Box(
                        modifier = Modifier
                            .size(200.dp, 150.dp)
                            .padding(start = if (index == 0) 10.dp else 0.dp, end = 10.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .clickableNoRipple { onClick() }
                    ) {
                        val context = LocalContext.current

                        AsyncImage(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth(),
                            model = ImageRequest.Builder(context)
                                .data(article.pic)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "image"
                        )

                        if (article.message != null) {
                            HtmlText(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(5.dp),
                                htmlText = article.message,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3,
                                fontSize = 12.sp,
                                lineHeight = 15.sp,
                                openLink = openLink
                            )
                        }
                    }
                }
            }
        }
    )
}

data class Article(
    val pic: String,
    val message: String?
)

fun Entity.toArticle() = Article(pic = pic, message = this.title)

fun com.anpe.coolbbsyou.net.model.index.Entity.toArticle() = Article(pic = pic, message = this.message)

fun com.anpe.coolbbsyou.net.model.index.Data.toArticles(): MutableList<Article> {
    val articles: MutableList<Article> = mutableListOf()

    entities.forEach {
        articles.add(it.toArticle())
    }

    return articles
}

fun Data.toArticles(): MutableList<Article> {
    val articles: MutableList<Article> = mutableListOf()

    entities.forEach {
        articles.add(it.toArticle())
    }

    return articles
}