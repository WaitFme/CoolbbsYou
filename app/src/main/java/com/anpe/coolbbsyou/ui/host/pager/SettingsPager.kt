package com.anpe.coolbbsyou.ui.host.pager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anpe.coolbbsyou.data.intent.MainIntent
import com.anpe.coolbbsyou.data.state.IndexImageState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsPager(viewModel: MainViewModel = viewModel()) {
    val scope = rememberCoroutineScope()

    val t by viewModel.indexImageState.collectAsState()

    val isNineGrid = when (t) {
        IndexImageState.ImageRow -> false
        IndexImageState.NineGrid -> true
    }

    Column(Modifier.fillMaxSize()) {
        var checked by remember {
            mutableStateOf(isNineGrid)
        }

        SettingsSwitchItem(
            title = "首页图片九宫格",
            tip = "首页图片内容是否开启九宫格排版",
            checked = checked,
            onCheckedChange = {
                checked = it

                scope.launch {
                    viewModel.channel.send(MainIntent.OpenNineGrid(checked))
                }
            }
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    modifier: Modifier = Modifier,
    title: String,
    tip: String? = null,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Box(
        modifier = modifier
            .clickable {
                onCheckedChange(!checked)
            }
            .fillMaxWidth()
            .padding(15.dp)
            .height(50.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            tip?.apply {
                Text(text = this, fontSize = 14.sp)
            }
        }
        Switch(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
    }
}