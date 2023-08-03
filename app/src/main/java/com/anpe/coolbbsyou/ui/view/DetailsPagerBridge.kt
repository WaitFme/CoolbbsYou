package com.anpe.coolbbsyou.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.state.DetailsState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.pager.DetailsPager


@Composable
fun DetailsPagerBridge(viewModel: MainViewModel, onBack: () -> Unit) {
    val detailsState by viewModel.detailsState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(if (isSystemInDarkTheme()) 0xff0d0d0d else 0xfff5f5f5))
    ) {
        when (detailsState) {
            is DetailsState.Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = (detailsState as DetailsState.Error).e
                )
            }

            is DetailsState.Idle -> {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(100.dp),
                        painter = painterResource(id = R.drawable.coolapk),
                        contentDescription = "icon",
                        tint = Color(if (isSystemInDarkTheme()) 0xff161616 else 0xfff1f1f1)
                    )
                    Text(
                        text = "Coolbbs",
                        fontWeight = FontWeight.Bold,
                        fontSize = 55.sp,
                        color = Color(if (isSystemInDarkTheme()) 0xff161616 else 0xfff1f1f1),
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            is DetailsState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is DetailsState.Success -> {
                val detailsEntity = (detailsState as DetailsState.Success).detailsEntity

                DetailsPager(
                    modifier = Modifier.fillMaxWidth(),
                    entity = detailsEntity,
                    onBack = onBack
                )
            }
        }
    }
}