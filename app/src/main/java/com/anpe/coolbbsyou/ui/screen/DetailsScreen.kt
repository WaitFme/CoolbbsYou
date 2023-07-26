package com.anpe.coolbbsyou.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.pager.DetailsPager

@Composable
fun DetailsScreen(navControllerScreen: NavController, id: Int?, viewModel: MainViewModel) {
    val details: DetailsEntity? = null
    var detailsEntity by remember {
        mutableStateOf(details)
    }

    if (LocalConfiguration.current.screenWidthDp >= 800) {
        navControllerScreen.popBackStack()
    }

    LaunchedEffect(key1 = true, block = {
        id?.apply {
            viewModel.channel.send(MainIntent.GetDetails(this))
        }

        viewModel.detailsState.collect {
            when (it) {
                is DetailsState.Error -> {}
                DetailsState.Idle -> {}
                DetailsState.Loading -> {}
                is DetailsState.Success -> detailsEntity = it.detailsEntity
            }
        }
    })

    detailsEntity?.apply {
        DetailsPager(entity = this)
    }
}