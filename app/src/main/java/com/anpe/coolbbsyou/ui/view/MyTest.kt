package com.anpe.coolbbsyou.ui.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyTest(entity: Any) {
    /*var refreshing by remember {
        mutableStateOf(false)
    }
    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            viewModel.channel.send(MainIntent.GetIndex)
        }
    })

    Box(Modifier.fillMaxSize()) {
        val list: List<Data> = listOf()
        var dataList by remember {
            mutableStateOf(list)
        }

        when (todayState) {
            is TodayState.Error -> {
                refreshing = false
                val error = (todayState as TodayState.Error).e
                Text(modifier = Modifier.align(Alignment.Center), text = error)
            }
            TodayState.Idle -> {

                refreshing = false
                Text(modifier = Modifier.align(Alignment.Center), text = "idle")

            }
            TodayState.Loading -> {

                if (dataList.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            }
            is TodayState.Success -> {


                refreshing = false
                dataList = (todayState as TodayState.Success).todayCoolEntity.data
            }
        }

        LazyColumn(
            modifier = Modifier
                .pullRefresh(refreshState)
                .fillMaxHeight(),
            contentPadding = PaddingValues(15.dp, 0.dp, 15.dp, 10.dp),
            content = {
                items(items = dataList) {
                }
            }
        )

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = refreshState,
            contentColor = MaterialTheme.colorScheme.surfaceTint
        )
    }*/
}