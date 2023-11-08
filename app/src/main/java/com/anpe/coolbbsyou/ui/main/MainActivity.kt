package com.anpe.coolbbsyou.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.ui.host.screen.ImageScreen
import com.anpe.coolbbsyou.ui.host.screen.LoginScreen
import com.anpe.coolbbsyou.ui.host.screen.MainScreen
import com.anpe.coolbbsyou.ui.host.screen.NewsScreen
import com.anpe.coolbbsyou.ui.host.screen.PostScreen
import com.anpe.coolbbsyou.ui.host.screen.SearchScreen
import com.anpe.coolbbsyou.ui.host.screen.SettingScreen
import com.anpe.coolbbsyou.ui.host.screen.SpaceScreen
import com.anpe.coolbbsyou.ui.host.screen.SplashScreen
import com.anpe.coolbbsyou.ui.host.screen.TopicScreen
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.theme.CoolbbsYouTheme
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToast
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CoolbbsYouTheme {
                rememberSystemUiController().setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = !isSystemInDarkTheme(),
                    isNavigationBarContrastEnforced = false
                )

                val windowSizeClass = calculateWindowSizeClass(activity = this)
                val displayFeatures = calculateDisplayFeatures(activity = this)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navControllerScreen = rememberNavController()

                    val viewModel: MainViewModel = viewModel()

                    NavHost(
                        navController = navControllerScreen,
                        startDestination = ScreenManager.SplashScreen.route,
                        builder = {
                            composable(route = ScreenManager.SplashScreen.route) {
                                SplashScreen(
                                    navControllerScreen = navControllerScreen,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.MainScreen.route) {
                                MainScreen(
                                    navControllerScreen = navControllerScreen,
                                    windowSizeClass = windowSizeClass,
                                    displayFeatures = displayFeatures,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.LoginScreen.route) {
                                LoginScreen(
                                    navControllerScreen = navControllerScreen,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.ImageScreen.route) {
                                ImageScreen(
                                    navControllerScreen = navControllerScreen,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.PostScreen.route) {
                                PostScreen(
                                    navControllerScreen = navControllerScreen,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.SettingScreen.route) {
                                SettingScreen(
                                    navControllerScreen = navControllerScreen,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.SpaceScreen.route) {
                                SpaceScreen(
                                    navControllerScreen = navControllerScreen,
                                    windowSizeClass = windowSizeClass,
                                    displayFeatures = displayFeatures,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.TopicScreen.route) {
                                TopicScreen(
                                    navControllerScreen = navControllerScreen,
                                    windowSizeClass = windowSizeClass,
                                    displayFeatures = displayFeatures,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.TopicScreen.route) {
                                NewsScreen(
                                    navControllerScreen = navControllerScreen,
                                    windowSizeClass = windowSizeClass,
                                    displayFeatures = displayFeatures,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.TopicScreen.route) {
                                SearchScreen(
                                    navControllerScreen = navControllerScreen,
                                    windowSizeClass = windowSizeClass,
                                    displayFeatures = displayFeatures,
                                    viewModel = viewModel
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}