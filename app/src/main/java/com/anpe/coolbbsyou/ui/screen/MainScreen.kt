package com.anpe.coolbbsyou.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.model.suggest.Data
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.network.data.state.SuggestState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.pager.DetailsPager
import com.anpe.coolbbsyou.ui.pager.HomePager
import com.anpe.coolbbsyou.ui.pager.MessagePager
import com.anpe.coolbbsyou.ui.pager.SettingsPager
import com.anpe.coolbbsyou.ui.pager.TodayCoolPager
import com.anpe.coolbbsyou.ui.pager.manager.PagerManager
import com.anpe.coolbbsyou.ui.view.MyScaffoldWithDetails
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navControllerScreen: NavHostController, viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()

    val details: DetailsEntity? = null
    var detailsEntity by remember {
        mutableStateOf(details)
    }
    val items = listOf(
        PagerManager.HomePager,
        PagerManager.MessagePager,
        PagerManager.SettingsPager,
    )

    LaunchedEffect(key1 = true, block = {
        viewModel.channel.send(MainIntent.GetIndex)
        viewModel.detailsState.collect {
            when (it) {
                is DetailsState.Error -> {}
                is DetailsState.Idle -> {}
                is DetailsState.Loading -> {}
                is DetailsState.Success -> detailsEntity = it.detailsEntity
            }
        }
    })

    MyScaffoldWithDetails(
        detailsBlock = { DetailsBlock(detailsEntity = detailsEntity) },
        topBar = { TopBar() },
        floatingActionButton = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.hierarchy?.any { it.route == PagerManager.HomePager.route } == true) {
                FloatingActionButton(onClick = {  }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        },
        railBar = { RailBar(navController = navController, items) },
        bottomBar = { BottomBar(navController = navController, items) },
        changeValue = 800.dp
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = PagerManager.HomePager.route,
            builder = {
                composable(PagerManager.HomePager.route) {
                    HomePager(navControllerScreen, navController, viewModel)
                }
                composable(PagerManager.MessagePager.route) { MessagePager() }
                composable(PagerManager.SettingsPager.route) { SettingsPager(viewModel) }
                composable(PagerManager.TodayCoolPager.route) { TodayCoolPager(navControllerScreen, viewModel) }
            }
        )
    }
}

@Composable
private fun DetailsBlock(detailsEntity: DetailsEntity?) {
    if (detailsEntity != null) {
        DetailsPager(
            modifier = Modifier.fillMaxWidth(),
            entity = detailsEntity
        )
    } else {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(if (isSystemInDarkTheme()) 0xff0d0d0d else 0xfff5f5f5))
        ) {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(viewModel: MainViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()

    var query by remember {
        mutableStateOf("")
    }

    var status by remember {
        mutableStateOf(false)
    }

    val data: List<Data> = listOf()
    var list by remember {
        mutableStateOf(data)
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.suggestState.collect {
            when (it) {
                is SuggestState.Error -> {}
                SuggestState.Idle -> {}
                SuggestState.Loading -> {}
                is SuggestState.Success -> list = it.suggestSearchEntity.data
            }
        }
    })

    Column {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
            query = query,
            onQueryChange = {
                query = it
                scope.launch {
                    if (query.isNotEmpty()) {
                        status = true
                        viewModel.channel.send(MainIntent.GetSuggestSearch(query))
                    } else {
                        status = false
                    }
                }
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text(text = "你热爱的就是你的生活.jpg", color = Color.Gray) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                        }
                    }
                    if (configuration.screenWidthDp < 800) {

                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp)),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp)),
                                model = "http://avatar.coolapk.com/data/001/18/61/29_avatar_middle.jpg?1652501380",
                                contentDescription = null
                            )
                        }
                    }
                }
            },
            shape = RoundedCornerShape(15.dp)
        ) {
            LazyColumn(content = {
                if (query.isNotEmpty()) {
                    items(list) {
                        if (it.entityType == "searchWord" && it.url.indexOf("searchTab://apk?keyword=") == -1) {
                            Text(text = it.title)
                        }
                    }
                }
            })
        }

        AnimatedVisibility(visible = status) {
            Popup(
                onDismissRequest = {
                    status = false
                }
            ) {
                Card(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(start = 15.dp, end = 15.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    LazyColumn(content = {
                        if (query.isNotEmpty()) {
                            items(list) {
                                if (
                                    it.entityType == "searchWord" &&
                                    it.url.indexOf("searchTab://apk?keyword=") == -1
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                start = 15.dp,
                                                end = 15.dp,
                                                top = 10.dp,
                                                bottom = 10.dp
                                            )
                                            .fillMaxWidth(),
                                        text = it.title
                                    )
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}

@Composable
private fun RailBar(navController: NavHostController, items: List<PagerManager>) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        content = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(0.15f),
                text = "Coolbbs",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(if (!isSystemInDarkTheme()) 0xff161616 else 0xfff1f1f1),
            )

            AsyncImage(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 30.dp)
                    .size(45.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = "http://avatar.coolapk.com/data/001/18/61/29_avatar_middle.jpg?1652501380",
                contentDescription = null
            )

            items.forEach { pager ->
                NavigationRailItem(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp),
                    selected = currentDestination?.hierarchy?.any { it.route == pager.route } == true,
                    icon = {
                        Icon(
                            when (pager.route) {
                                PagerManager.HomePager.route -> painterResource(id = R.drawable.baseline_home_24)
                                PagerManager.MessagePager.route -> painterResource(id = R.drawable.baseline_message_24)
                                PagerManager.SettingsPager.route -> painterResource(id = R.drawable.baseline_settings_24)
                                else -> painterResource(id = R.drawable.baseline_error_24)
                            },
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            stringResource(pager.resourceId),
                            fontSize = 14.sp
                        )
                    },
                    alwaysShowLabel = true,
                    onClick = {
                        navController.navigate(pager.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun BottomBar(navController: NavHostController, items: List<PagerManager>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { pager ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == pager.route } == true,
                icon = {
                    Icon(
                        when (pager.route) {
                            PagerManager.HomePager.route -> painterResource(id = R.drawable.baseline_home_24)
                            PagerManager.MessagePager.route -> painterResource(id = R.drawable.baseline_message_24)
                            PagerManager.SettingsPager.route -> painterResource(id = R.drawable.baseline_settings_24)
                            else -> painterResource(id = R.drawable.baseline_error_24)
                        },
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        stringResource(pager.resourceId),
                        fontSize = 14.sp
                    )
                },
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(pager.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}