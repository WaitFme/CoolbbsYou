package com.anpe.coolbbsyou.ui.host.screen.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R

sealed class ScreenManager(val route: String, @StringRes val resourceId: Int) {
    data object SplashScreen: ScreenManager("SplashScreen", R.string.splash_screen)

    data object MainScreen: ScreenManager("MainScreen", R.string.main_screen)

    data object LoginScreen: ScreenManager("LoginScreen", R.string.login_screen)

    data object FullImageScreen: ScreenManager("FullImageScreen", R.string.full_image_screen)

    data object PostScreen: ScreenManager("PostScreen", R.string.full_image_screen)
}