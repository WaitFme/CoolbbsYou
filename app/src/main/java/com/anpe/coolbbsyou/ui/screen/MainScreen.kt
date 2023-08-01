package com.anpe.coolbbsyou.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.state.SuggestState
import com.anpe.coolbbsyou.ui.innerScreen.TodaySelectionScreen
import com.anpe.coolbbsyou.ui.innerScreen.manager.InnerScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.pager.HomePager
import com.anpe.coolbbsyou.ui.pager.MessagePager
import com.anpe.coolbbsyou.ui.pager.SettingsPager
import com.anpe.coolbbsyou.ui.pager.manager.PagerManager
import com.anpe.coolbbsyou.ui.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.view.CustomProgress
import com.anpe.coolbbsyou.ui.view.DetailsPagerBridge
import com.anpe.coolbbsyou.ui.view.ResponsiveLayout
import com.anpe.coolbbsyou.util.SharedPreferencesUtils.Companion.getBoolean
import com.anpe.coolbbsyou.util.SharedPreferencesUtils.Companion.getInt
import com.anpe.coolbbsyou.util.SharedPreferencesUtils.Companion.getString
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navControllerScreen: NavHostController, viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val navControllerInnerScreen = rememberNavController()
    val navControllerPager = rememberNavController()

    val items = listOf(
        PagerManager.HomePager,
        PagerManager.MessagePager,
        PagerManager.SettingsPager,
    )

    ResponsiveLayout(
        railBar = {
            RailBar(
                navControllerScreen = navControllerScreen,
                navControllerInnerScreen = navControllerInnerScreen,
                navControllerPager = navControllerPager,
                items = items,
                avatarClick = {
                    val uid = getInt("uid")
                    if (uid != -1) {
                        scope.launch {
                            viewModel.sendIntent(MainIntent.GetProfile(uid))
                        }
                    }
                }
            )
        },
        changeValue = 800.dp,
        detailsBlock = { DetailsBlock(viewModel) },
        content = {
            NavHost(
                navController = navControllerInnerScreen,
                startDestination = InnerScreenManager.HomeInnerScreen.route,
                builder = {
                    composable(route = InnerScreenManager.HomeInnerScreen.route) {
                        Scaffold(
                            topBar = { TopBar(navControllerScreen, viewModel) },
                            floatingActionButton = {
                                val navBackStackEntry by navControllerPager.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                if (currentDestination?.hierarchy?.any { it.route == PagerManager.HomePager.route } == true) {
                                    FloatingActionButton(onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            bottomBar = {
                                if (configuration.screenWidthDp < 800) {
                                    BottomBar(navController = navControllerPager, items)
                                }
                            },
                        ) {
                            NavHost(
                                modifier = Modifier.padding(it),
                                navController = navControllerPager,
                                startDestination = PagerManager.HomePager.route,
                                builder = {
                                    composable(route = PagerManager.HomePager.route) {
                                        HomePager(
                                            navControllerScreen = navControllerScreen,
                                            navControllerInnerScreen = navControllerInnerScreen,
                                            navControllerPager = navControllerPager,
                                            viewModel = viewModel
                                        )
                                    }
                                    composable(route = PagerManager.MessagePager.route) {
                                        MessagePager(viewModel = viewModel)
                                    }
                                    composable(route = PagerManager.SettingsPager.route) {
                                        SettingsPager(viewModel = viewModel)
                                    }
                                }
                            )
                        }
                    }

                    composable(route = InnerScreenManager.TodaySelectionInnerScreen.route) {
                        TodaySelectionScreen(
                            navControllerScreen = navControllerScreen,
                            navControllerInnerScreen = navControllerInnerScreen,
                            viewModel = viewModel
                        )
                    }
                }
            )
        }
    )

    /*MyScaffoldWithDetails(
        detailsBlock = { DetailsBlock(viewModel) },
        topBar = { TopBar(navControllerScreen, viewModel) },
        floatingActionButton = {
            val navBackStackEntry by navControllerPager.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.hierarchy?.any { it.route == PagerManager.HomePager.route } == true) {
                FloatingActionButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        },
        railBar = {
            RailBar(
                navControllerScreen = navControllerScreen,
                navControllerPager = navControllerPager,
                items = items,
                avatarClick = {
                    val uid = getInt("uid")
                    if (uid != -1) {
                        scope.launch {
                            viewModel.sendIntent(MainIntent.GetProfile(uid))
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = navControllerPager, items) },
        changeValue = 800.dp
    ) { pv ->
        NavHost(
            modifier = Modifier.padding(pv),
            navController = navControllerPager,
            startDestination = PagerManager.HomePager.route,
            builder = {
                composable(route = PagerManager.HomePager.route) {
                    HomePager(navControllerScreen, navControllerPager, viewModel)
                }
                composable(route = PagerManager.MessagePager.route) {
                    MessagePager(viewModel)
                }
                composable(route = PagerManager.SettingsPager.route) {
                    SettingsPager(viewModel)
                }
                composable(route = PagerManager.TodayCoolPager.route) {
                    TodayCoolPager(
                        navControllerInnerScreen = navControllerScreen,
                        navControllerPager = navControllerPager,
                        viewModel = viewModel
                    )
                }
            }
        )
    }*/
}

@Composable
private fun DetailsBlock(viewModel: MainViewModel) {
    DetailsPagerBridge(viewModel = viewModel, onBack = { })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    navControllerScreen: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val suggestState by viewModel.suggestState.collectAsState()

    var query by remember {
        mutableStateOf("")
    }

    var status by remember {
        mutableStateOf(false)
    }

    var dialog by remember {
        mutableStateOf(false)
    }

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
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_tip),
                    color = Color.Gray
                )
            },
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
                        /*IconButton(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray),
                            onClick = {
                                val uid = getInt("uid")
                                if (uid != -1) {
                                    scope.launch {
                                        viewModel.sendIntent(MainIntent.GetProfile(uid))
                                    }
                                }
                                dialog = !dialog
                            }
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp)),
                                model = ImageRequest.Builder(context)
                                    .data(
                                        getString("userAvatar")
                                            ?: R.drawable.ic_user_avatar
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null
                            )
                        }*/

                        AsyncImage(
                            modifier = Modifier
                                .padding(6.dp, 6.dp, 2.dp, 6.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray)
                                .clickableNoRipple {
                                    val uid = getInt("uid")
                                    if (uid != -1) {
                                        scope.launch {
                                            viewModel.sendIntent(MainIntent.GetProfile(uid))
                                        }
                                    }
                                    dialog = !dialog
                                },
                            model = ImageRequest.Builder(context)
                                .data(
                                    getString("userAvatar")
                                        ?: R.drawable.ic_user_avatar
                                )
                                .crossfade(true)
                                .build(),
                            contentDescription = null
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp)
        ) {
            Box {
                when (suggestState) {
                    is SuggestState.Error -> {
                        Text(
                            modifier = Modifier.align(
                                Alignment.Center
                            ), text = (suggestState as SuggestState.Error).e
                        )
                    }

                    SuggestState.Idle -> {
                        Text(
                            modifier = Modifier.align(
                                Alignment.Center
                            ), text = "Idle"
                        )
                    }

                    SuggestState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )

                    is SuggestState.Success -> {
                        val dataList =
                            (suggestState as SuggestState.Success).suggestSearchEntity.data

                        LazyColumn(content = {
                            if (query.isNotEmpty()) {
                                items(dataList) {
                                    if (it.entityType == "searchWord" && it.url.indexOf("searchTab://apk?keyword=") == -1) {
                                        Text(text = it.title)
                                    }
                                }
                            }
                        })
                    }
                }
            }
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
                    Box(modifier = Modifier.fillMaxWidth()) {
                        when (suggestState) {
                            is SuggestState.Error -> {
                                Text(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ), text = (suggestState as SuggestState.Error).e
                                )
                            }

                            SuggestState.Idle -> {
                                Text(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ), text = "Idle"
                                )
                            }

                            SuggestState.Loading -> CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )

                            is SuggestState.Success -> {
                                var dataList =
                                    (suggestState as SuggestState.Success).suggestSearchEntity.data

                                if (dataList.size >= 10) {
                                    dataList = dataList.subList(0, 9)
                                }

                                LazyColumn(content = {
                                    if (query.isNotEmpty()) {
                                        items(dataList) {
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
        }

        if (dialog) {
            BackHandler {
                dialog = false
            }
        }

        if (dialog) {
            CustomDialog(
                onDismissRequest = { dialog = false },
                navControllerScreen = navControllerScreen
            )
        }
    }
}

@Composable
private fun RailBar(
    navControllerScreen: NavHostController,
    navControllerInnerScreen: NavHostController,
    navControllerPager: NavHostController,
    items: List<PagerManager>,
    avatarClick: () -> Unit
) {
    var dialog by remember {
        mutableStateOf(false)
    }

    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        content = {
            val context = LocalContext.current

            val navBackStackEntry by navControllerPager.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val navBackStackEntryInner by navControllerInnerScreen.currentBackStackEntryAsState()
            val currentDestinationInner = navBackStackEntryInner?.destination

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
                    .clickableNoRipple {
                        avatarClick()
                        dialog = !dialog
                    }
                    .padding(top = 10.dp, bottom = 30.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(7.dp)),
                model = ImageRequest.Builder(context)
                    .data(
                        getString("userAvatar")
                            ?: R.drawable.baseline_supervised_user_circle_24
                    )
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
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
                        navControllerPager.popBackStack()
                        navControllerPager.navigate(pager.route) {
                            popUpTo(navControllerPager.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        if (currentDestinationInner?.hierarchy?.any { it.route == InnerScreenManager.HomeInnerScreen.route } == false) {
                            navControllerInnerScreen.popBackStack()
                            navControllerInnerScreen.navigate(InnerScreenManager.HomeInnerScreen.route) {
                                popUpTo(navControllerInnerScreen.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    )

    if (dialog) {
        BackHandler {
            dialog = false
        }
    }

    if (dialog) {
        CustomDialog(
            onDismissRequest = { dialog = false },
            navControllerScreen = navControllerScreen
        )
    }
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

@Composable
private fun CustomDialog(
    onDismissRequest: () -> Unit,
    navControllerScreen: NavHostController
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .width(400.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current

            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp),
                text = stringResource(id = R.string.app_name),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            ConstraintLayout(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                val (
                    avatarRef,
                    usernameRef,
                    userLevelRef,
                    experienceRef,
                    experienceSeekRef,
                    funRef,
                    spacerRef,
                    itemRef
                ) = createRefs()

                AsyncImage(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .constrainAs(avatarRef) {
                            start.linkTo(parent.start, 15.dp)
                            top.linkTo(parent.top, 15.dp)
                        }
                        .clickableNoRipple {
                            if (!getBoolean("isLogin")) {
                                onDismissRequest()
                                navControllerScreen.navigate(ScreenManager.LoginScreen.route)
                            }
                        },
                    model = ImageRequest.Builder(context)
                        .data(
                            getString("userAvatar")
                                ?: R.drawable.baseline_supervised_user_circle_24
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = "avatar"
                )

                Text(
                    modifier = Modifier
                        .constrainAs(usernameRef) {
                            start.linkTo(avatarRef.end, 10.dp)
                            top.linkTo(avatarRef.top)
                        },
                    text = getString("username") ?: "游客",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    modifier = Modifier
                        .constrainAs(userLevelRef) {
                            start.linkTo(avatarRef.end, 10.dp)
                            top.linkTo(usernameRef.bottom, 5.dp)
                        },
                    text = "Lv.${getInt("level")}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    modifier = Modifier
                        .constrainAs(experienceRef) {
                            start.linkTo(userLevelRef.end, 25.dp)
                            top.linkTo(userLevelRef.top)
                        },
                    text = "${getInt("experience")}/${getInt("next_level_experience")}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                CustomProgress(
                    modifier = Modifier
                        .constrainAs(experienceSeekRef) {
                            start.linkTo(avatarRef.end, 10.dp)
                            top.linkTo(userLevelRef.bottom, 5.dp)
                            end.linkTo(experienceRef.end)
                            width = Dimension.fillToConstraints
                        },
                    currentValue = getInt("experience"),
                    strokeWidth = 10f,
                    maxValue = getInt("next_level_experience"),
                    primaryColor = MaterialTheme.colorScheme.primary,
                    secondaryColor = MaterialTheme.colorScheme.primaryContainer
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(funRef) {
                            start.linkTo(parent.start)
                            top.linkTo(experienceSeekRef.bottom, 20.dp)
                            end.linkTo(parent.end)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val follow = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(getInt("follow").toString())
                        }
                        append("\n${stringResource(id = R.string.follow)}")
                    }
                    val fans = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(getInt("fans").toString())
                        }
                        append("\n${stringResource(id = R.string.fans)}")
                    }
                    val feed = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(getInt("feed").toString())
                        }
                        append("\n${stringResource(id = R.string.feed)}")
                    }

                    Text(
                        modifier = Modifier.weight(1f),
                        text = follow,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = fans,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = feed,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .constrainAs(spacerRef) {
                            top.linkTo(funRef.bottom, 15.dp)
                        }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(itemRef) {
                            top.linkTo(spacerRef.bottom, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                        }
                ) {
                    IconText(text = "我的常去", icon = R.drawable.baseline_hdr_strong_24)
                    IconText(text = "我的话题", icon = R.drawable.baseline_label_24)
                    IconText(text = "浏览历史", icon = R.drawable.baseline_history_24)
                    IconText(text = "我的收藏", icon = R.drawable.baseline_star_24)
                    if (getBoolean("isLogin")) {
                        IconText(text = "退出登陆", icon = R.drawable.baseline_exit_to_app_24)
                    }
                }
            }

            IconText(
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp),
                text = "关于", icon = R.drawable.baseline_error_24
            )
        }
    }
}

@Composable
private fun IconText(modifier: Modifier = Modifier, text: String, icon: Int? = null) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .size(20.dp),
                painter = painterResource(id = icon),
                contentDescription = "icon"
            )
        }

        Text(
            modifier = Modifier
                .padding(start = if (icon == null) 30.dp else 15.dp),
            text = text, fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}