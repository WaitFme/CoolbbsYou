package com.anpe.coolbbsyou.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anpe.coolbbsyou.ui.host.screen.FullImageScreen
import com.anpe.coolbbsyou.ui.host.screen.LoginScreen
import com.anpe.coolbbsyou.ui.host.screen.MainScreen
import com.anpe.coolbbsyou.ui.host.screen.SplashScreen
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.theme.CoolbbsYouTheme
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
                            composable(route = ScreenManager.FullImageScreen.route) {
                                FullImageScreen(
                                    navControllerScreen = navControllerScreen,
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