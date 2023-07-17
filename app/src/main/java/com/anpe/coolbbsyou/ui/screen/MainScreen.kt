package com.anpe.coolbbsyou.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anpe.coolbbsyou.ui.view.MyScaffold
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.pager.DetailsPager
import com.anpe.coolbbsyou.ui.pager.HomePager
import com.anpe.coolbbsyou.ui.pager.MessagePager
import com.anpe.coolbbsyou.ui.pager.SettingsPager
import com.anpe.coolbbsyou.ui.pager.manager.PagerManager
import com.anpe.coolbbsyou.ui.view.MyScaffoldWithDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navControllerScreen: NavHostController, viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val configuration = LocalConfiguration.current

    val details: DetailsEntity? = null
    var detailsEntity by remember {
        mutableStateOf(details)
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.detailsState.collect {
            when (it) {
                is DetailsState.Error -> {}
                DetailsState.Idle -> {}
                DetailsState.Loading -> {}
                is DetailsState.Success -> detailsEntity = it.detailsEntity
            }
        }
    })

    MyScaffoldWithDetails(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        detailsBlock = { DetailsBlock(detailsEntity = detailsEntity, viewModel = viewModel) },
        topBar = { TopAppBar(scrollBehavior = scrollBehavior, title = { Text(text = stringResource(id = R.string.app_name)) }) },
        railBar = { RailBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController) },
        changeValue = 800.dp
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = PagerManager.HomePager.route,
            builder = {
                composable(PagerManager.HomePager.route) {
                    HomePager(
                        navControllerScreen,
                        viewModel
                    )
                }
                composable(PagerManager.MessagePager.route) { MessagePager() }
                composable(PagerManager.SettingsPager.route) { SettingsPager() }
            }
        )
    }
}

@Composable
private fun DetailsBlock(detailsEntity: DetailsEntity?, viewModel: MainViewModel) {
    if (detailsEntity != null) {
        DetailsPager(
            modifier = Modifier.fillMaxWidth(),
            entity = detailsEntity,
            viewModel = viewModel
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

@Composable
private fun RailBar(navController: NavHostController) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        content = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val items = listOf(
                PagerManager.HomePager,
                PagerManager.MessagePager,
                PagerManager.SettingsPager,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
        }
    )
}

@Composable
private fun BottomBar(navController: NavHostController) {
    NavigationBar {
        val vm: MainViewModel = viewModel()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val items = listOf(
            PagerManager.HomePager,
            PagerManager.MessagePager,
            PagerManager.SettingsPager,
        )

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