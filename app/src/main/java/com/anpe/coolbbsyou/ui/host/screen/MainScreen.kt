package com.anpe.coolbbsyou.ui.host.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.DetailsState
import com.anpe.coolbbsyou.intent.state.SuggestState
import com.anpe.coolbbsyou.ui.host.innerScreen.SearchInnerScreen
import com.anpe.coolbbsyou.ui.host.innerScreen.TodaySelectionInnerScreen
import com.anpe.coolbbsyou.ui.host.innerScreen.TopicInnerScreen
import com.anpe.coolbbsyou.ui.host.innerScreen.UserSpaceInnerScreen
import com.anpe.coolbbsyou.ui.host.innerScreen.manager.InnerScreenManager
import com.anpe.coolbbsyou.ui.host.pager.DetailPager
import com.anpe.coolbbsyou.ui.host.pager.HomePager
import com.anpe.coolbbsyou.ui.host.pager.MessagePager
import com.anpe.coolbbsyou.ui.host.pager.SettingsPager
import com.anpe.coolbbsyou.ui.host.pager.manager.PagerManager
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.CustomProgress
import com.anpe.coolbbsyou.ui.view.TwoPaneResponsiveLayout
import com.anpe.coolbbsyou.util.MyApplication
import com.anpe.coolbbsyou.util.SharedPreferencesUtils.Companion.getBoolean
import com.anpe.coolbbsyou.util.SharedPreferencesUtils.Companion.getInt
import com.anpe.coolbbsyou.util.SharedPreferencesUtils.Companion.getString
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToastString
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navControllerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val navControllerInnerScreen = rememberNavController()
    val navControllerPager = rememberNavController()

    val homeItem = NavigationItem(
        title = stringResource(id = PagerManager.HomePager.resourceId),
        icon = painterResource(id = R.drawable.baseline_home_24),
        route = PagerManager.HomePager.route
    )
    val messageItem = NavigationItem(
        title = stringResource(id = PagerManager.MessagePager.resourceId),
        icon = painterResource(id = R.drawable.baseline_message_24),
        route = PagerManager.MessagePager.route,
    )
    val settingItem = NavigationItem(
        title = stringResource(id = PagerManager.SettingsPager.resourceId),
        icon = painterResource(id = R.drawable.baseline_settings_24),
        route = PagerManager.SettingsPager.route,
    )

    val items = listOf(homeItem, messageItem, settingItem)

    var dialog by remember { mutableStateOf(false) }

    var isDetailOpen by rememberSaveable { mutableStateOf(false) }

    TwoPaneResponsiveLayout(
        isDetailOpen = isDetailOpen,
        setIsDetailOpen = {
            isDetailOpen = it
        },
        windowSizeClass = windowSizeClass,
        displayFeatures = displayFeatures,
        railBar = {
            var index by rememberSaveable {
                mutableIntStateOf(0)
            }

            RailBar(
                items = items,
                selectedItemIndex = index,
                avatarClick = {
                    val uid = getInt("uid")
                    if (uid != -1) {
                        scope.launch {
                            viewModel.channel.send(MainEvent.GetProfile(uid))
                        }
                    }
                    dialog = !dialog
                },
                onNavigate = {
                    index = it
                    navControllerPager.popBackStack()
                    navControllerPager.navigate(items[index].route) {
                        popUpTo(navControllerPager.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        list = {
            ListBlock(
                navControllerScreen = navControllerScreen,
                navControllerInnerScreen = navControllerInnerScreen,
                navControllerPager = navControllerPager,
                items = items,
                windowSizeClass = windowSizeClass,
                showDialog = { dialog = it },
                setIsDetailOpen = { isDetailOpen = it },
                viewModel = viewModel
            )
        },
        detail = {
            DetailBlock(
                navControllerInnerScreen = navControllerInnerScreen,
                windowSizeClass = windowSizeClass,
                setIsDetailOpen = { isDetailOpen = it },
                viewModel = viewModel
            )
        }
    )

    if (dialog) {
        BackHandler {
            dialog = false
        }
        CustomDialog(
            onDismissRequest = { dialog = false },
            onLogout = {
                scope.launch {
                    viewModel.channel.send(MainEvent.LogoutAccount)
                    dialog = false
                    MyApplication.context.showToastString("已退出登陆")
                }
            },
            onLogin = {
                if (!getBoolean(name = Constants.CONFIG_PREFS, key = "LOGIN_STATUS")) {
                    dialog = false
                    navControllerScreen.navigate(ScreenManager.LoginScreen.route)
                }
            },
            viewModel = viewModel
        )
    }
}

@Composable
private fun ListBlock(
    navControllerScreen: NavHostController,
    navControllerInnerScreen: NavHostController,
    navControllerPager: NavHostController,
    items: List<NavigationItem>,
    windowSizeClass: WindowSizeClass,
    showDialog: (Boolean) -> Unit,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val widthSizeClass by rememberUpdatedState(windowSizeClass.widthSizeClass)

    NavHost(
        modifier = Modifier,
        navController = navControllerInnerScreen,
        startDestination = InnerScreenManager.HomeInnerScreen.route,
        builder = {
            composable(route = InnerScreenManager.HomeInnerScreen.route) {
                Scaffold(
                    topBar = {
                        TopBar(
                            widthSizeClass = widthSizeClass,
                            onAvatarClick = {
                                val uid = getInt("uid")
                                if (uid != -1) {
                                    scope.launch {
                                        viewModel.channel.send(MainEvent.GetProfile(uid))
                                    }
                                }
                                showDialog(true)
                            },
                            onSearch = {
                                scope.launch {
                                    viewModel.channel.send(MainEvent.GetSearch(it))
                                }
                                navControllerInnerScreen.navigate(InnerScreenManager.SearchInnerScreen.route)
                            },
                            viewModel = viewModel
                        )
                    },
                    floatingActionButton = {
                        var dialog by remember {
                            mutableStateOf(false)
                        }

                        val navBackStackEntry by navControllerPager.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.hierarchy?.any { it.route == PagerManager.HomePager.route } == true) {
                            FloatingActionButton(onClick = { navControllerScreen.navigate(ScreenManager.PostScreen.route) }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }

                        AnimatedVisibility(visible = dialog) {
                            AlertDialog(
                                onDismissRequest = { dialog = false },
                                confirmButton = {
                                    Button(onClick = {
                                        dialog = false
                                        scope.launch {
                                            viewModel.channel.send(MainEvent.CreateFeed("SEND TIME: ${System.currentTimeMillis()}"))
                                        }
                                    }) {
                                        Text(text = "sub")
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        when (widthSizeClass) {
                            WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> {
                                var index by rememberSaveable {
                                    mutableStateOf(0)
                                }

                                BottomBar(
                                    items = items,
                                    selectedItemIndex = index,
                                    onNavigate = {
                                        index = it
                                        navControllerPager.navigate(items[index].route) {
                                            popUpTo(navControllerPager.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }

                            WindowWidthSizeClass.Expanded -> {}
                            else -> {}
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
                                    setIsDetailOpen = setIsDetailOpen,
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
                TodaySelectionInnerScreen(
                    navControllerScreen = navControllerScreen,
                    navControllerInnerScreen = navControllerInnerScreen,
                    setIsDetailOpen = setIsDetailOpen,
                    viewModel = viewModel
                )
            }

            composable(route = InnerScreenManager.SearchInnerScreen.route) {
                SearchInnerScreen(
                    navControllerInnerScreen = navControllerInnerScreen,
                    setIsDetailOpen = setIsDetailOpen,
                    viewModel = viewModel
                )
            }

            composable(route = InnerScreenManager.UserSpaceInnerScreen.route) {
                UserSpaceInnerScreen(
                    navControllerInnerScreen = navControllerInnerScreen,
                    setIsDetailOpen = setIsDetailOpen,
                    viewModel = viewModel
                )
            }

            composable(route = InnerScreenManager.TopicInnerScreen.route) {
                TopicInnerScreen(
                    navControllerInnerScreen = navControllerInnerScreen,
                    setIsDetailOpen = setIsDetailOpen,
                    viewModel = viewModel
                )
            }
        }
    )
}

@Composable
private fun DetailBlock(
    navControllerInnerScreen: NavHostController,
    windowSizeClass: WindowSizeClass,
    setIsDetailOpen: (Boolean) -> Unit,
    viewModel: MainViewModel
) {
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
                val detailsModel = (detailsState as DetailsState.Success).detailsEntity

                LaunchedEffect(key1 = detailsModel) {
                    viewModel.channel.send(MainEvent.GetReply(detailsModel.data.id))
                }

                DetailPager(
                    modifier = Modifier.fillMaxWidth(),
                    navControllerInnerScreen = navControllerInnerScreen,
                    detailsModel = detailsModel,
                    windowSizeClass = windowSizeClass,
                    setIsDetailOpen = setIsDetailOpen,
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    widthSizeClass: WindowWidthSizeClass,
    onAvatarClick: () -> Unit,
    onSearch: (String) -> Unit,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val suggestState by viewModel.suggestState.collectAsState()

    var query by remember {
        mutableStateOf("")
    }

    var status by remember {
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
                        viewModel.channel.send(MainEvent.GetSuggestSearch(query))
                    } else {
                        status = false
                    }
                }
            },
            onSearch = {
                onSearch(it)
            },
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

                    if (widthSizeClass == WindowWidthSizeClass.Compact) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(6.dp, 6.dp, 2.dp, 6.dp)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickableNoRipple {
                                    onAvatarClick()
                                },
                            model = ImageRequest.Builder(context)
                                .data(
                                    getString(
                                        key = "USER_AVATAR",
                                        name = Constants.USER_INFO_PREFS
                                    ) ?: R.drawable.ic_user_background
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
    }
}

@Composable
private fun RailBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    avatarClick: () -> Unit,
    onNavigate: (Int) -> Unit
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        header = {
            val context = LocalContext.current

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
                    }
                    .padding(top = 10.dp, bottom = 30.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(7.dp)),
                model = ImageRequest.Builder(context)
                    .data(
                        getString(
                            key = "USER_AVATAR",
                            name = Constants.USER_INFO_PREFS
                        ) ?: R.drawable.ic_user_background
                    )
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        },
        content = {
            ConstraintLayout(modifier = Modifier.fillMaxHeight()) {
                val (homeRef, messageRef, settingRef) = createRefs()
                val refList = listOf(homeRef, messageRef, settingRef)

                items.forEachIndexed { index, item ->
                    NavigationRailItem(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .constrainAs(refList[index]) {
                                when (index) {
                                    0 -> {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                    }

                                    1 -> {
                                        start.linkTo(parent.start)
                                        top.linkTo(homeRef.bottom)
                                        end.linkTo(parent.end)
                                    }

                                    2 -> {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }

                                    else -> {}
                                }
                            },
                        selected = selectedItemIndex == index,
                        icon = { NavigationIcon(item = item) },
                        label = if (index != 2) {
                            { Text(text = item.title, fontSize = 14.sp) }
                        } else {
                            null
                        },
                        alwaysShowLabel = true,
                        onClick = { onNavigate(index) }
                    )
                }
            }
        }
    )
}

@Composable
private fun BottomBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onNavigate: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItemIndex,
                icon = { NavigationIcon(item = item) },
                label = { Text(text = item.title, fontSize = 14.sp) },
                alwaysShowLabel = false,
                onClick = { onNavigate(index) }
            )
        }
    }
}

@Composable
private fun CustomDialog(
    onDismissRequest: () -> Unit,
    onLogout: () -> Unit,
    onLogin: () -> Unit,
    viewModel: MainViewModel
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

            getInt(name = Constants.USER_INFO_PREFS, key = "UID", defValue = -1).apply {
                if (this != -1) {
                    LaunchedEffect(key1 = true, block = {
                        viewModel.channel.send(MainEvent.GetProfile(this@apply))
                    })
                }
            }

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
                        .clickableNoRipple {
                            onLogin()
                        }
                        .constrainAs(avatarRef) {
                            start.linkTo(parent.start, 15.dp)
                            top.linkTo(parent.top, 15.dp)
                        },
                    model = ImageRequest.Builder(context)
                        .data(
                            getString(key = "USER_AVATAR", name = Constants.USER_INFO_PREFS)
                                ?: R.drawable.ic_user_background
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
                    text = getString(
                        key = "USER_NAME",
                        defValue = "游客",
                        name = Constants.USER_INFO_PREFS
                    )!!,
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
                    text = "Lv.${
                        getInt(
                            key = "LEVEL",
                            defValue = -1,
                            name = Constants.USER_INFO_PREFS
                        )
                    }", // level
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
                    text = "${
                        getInt(
                            key = "EXPERIENCE",
                            defValue = 0,
                            name = Constants.USER_INFO_PREFS
                        )
                    }/${
                        getInt(
                            key = "NEXT_LEVEL_EXPERIENCE",
                            defValue = 100,
                            name = Constants.USER_INFO_PREFS
                        )
                    }",
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
                    currentValue = getInt(
                        key = "EXPERIENCE",
                        defValue = 0,
                        name = Constants.USER_INFO_PREFS
                    ),
                    strokeWidth = 10f,
                    maxValue = getInt(
                        key = "NEXT_LEVEL_EXPERIENCE",
                        defValue = 100,
                        name = Constants.USER_INFO_PREFS
                    ),
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
                            append(getInt(name = Constants.USER_INFO_PREFS, key = "FOLLOW", defValue = -1).toString())
                        }
                        append("\n${stringResource(id = R.string.follow)}")
                    }
                    val fans = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(getInt(name = Constants.USER_INFO_PREFS, key = "FANS", defValue = -1).toString())
                        }
                        append("\n${stringResource(id = R.string.fans)}")
                    }
                    val feed = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(getInt(name = Constants.USER_INFO_PREFS, key = "FEED", defValue = -1).toString())
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
                    if (getBoolean(name = Constants.CONFIG_PREFS, key = "LOGIN_STATUS")) {
                        IconText(
                            modifier = Modifier.clickableNoRipple {
                                onLogout()
                            },
                            text = "退出登陆",
                            icon = R.drawable.baseline_exit_to_app_24
                        )
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

data class NavigationItem(
    val title: String,
    val icon: Painter,
    val route: String,
    val badgeCount: Int? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationIcon(item: NavigationItem) {
    BadgedBox(
        badge = {
            item.badgeCount?.let {
                Badge {
                    Text(text = if (it <= 99) item.badgeCount.toString() else "99+")
                }
            }
        }
    ) {
        Icon(
            painter = item.icon,
            contentDescription = item.title
        )
    }
}