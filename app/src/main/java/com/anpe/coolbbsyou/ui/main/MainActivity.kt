package com.anpe.coolbbsyou.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anpe.coolbbsyou.ui.screen.DetailsScreen
import com.anpe.coolbbsyou.ui.screen.LoginScreen
import com.anpe.coolbbsyou.ui.screen.MainScreen
import com.anpe.coolbbsyou.ui.screen.SplashScreen
import com.anpe.coolbbsyou.ui.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.theme.CoolbbsYouTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CoolbbsYouTheme {
                rememberSystemUiController().run {
                    setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkTheme()
                    )
                    setNavigationBarColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkTheme(),
                        navigationBarContrastEnforced = false
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: MainViewModel = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = ScreenManager.SplashScreen.route,
                        builder = {
                            composable(route = ScreenManager.SplashScreen.route) {
                                SplashScreen(
                                    navControllerScreen = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable(route = ScreenManager.MainScreen.route) {
                                MainScreen(
                                    navControllerScreen = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable(
                                route = "${ScreenManager.DetailsScreen.route}/{id}",
                                arguments = listOf(navArgument("id") { type = NavType.IntType })
                            ) {
                                DetailsScreen(
                                    navControllerScreen = navController,
                                    id = it.arguments?.getInt("id"),
                                    viewModel = viewModel
                                )
                            }
                            composable(ScreenManager.LoginScreen.route) {
                                LoginScreen(
                                    navControllerScreen = navController,
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