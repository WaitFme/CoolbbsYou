package com.anpe.coolbbsyou.ui.pager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessagePager() {
    Column {
        MessageItem(title = "我的动态")
        MessageItem(title = "我的评论")
        MessageItem(title = "我收到的赞")
        MessageItem(title = "好友关注")
        MessageItem(title = "私信")
    }
}

@Composable
private fun MessageItem(
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