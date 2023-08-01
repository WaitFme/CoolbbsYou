package com.anpe.coolbbsyou.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.DetailsPagerBridge

@Composable
fun DetailsScreen(navControllerScreen: NavController, viewModel: MainViewModel) {
    /*val details: DetailsEntity? = null
    var detailsEntity by remember {
        mutableStateOf(details)
    }

    if (LocalConfiguration.current.screenWidthDp >= 800) {
        navControllerScreen.popBackStack()
    }

    LaunchedEffect(key1 = true, block = {
        *//*id?.apply {
            viewModel.channel.send(MainIntent.GetDetails(this))
        }*//*

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
        DetailsPager(entity = this, onBack = {
            navControllerScreen.popBackStack()
        })
    }*/

    DetailsPagerBridge(viewModel = viewModel, onBack = {
        navControllerScreen.popBackStack()
    })
}