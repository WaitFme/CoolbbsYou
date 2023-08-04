package com.anpe.coolbbsyou.ui.host.screen.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R

sealed class ScreenManager(val route: String, @StringRes val resourceId: Int) {
    object SplashScreen: ScreenManager("SplashScreen", R.string.splash_screen)

    object MainScreen: ScreenManager("MainScreen", R.string.main_screen)

    object LoginScreen: ScreenManager("LoginScreen", R.string.login_screen)

    object FullImageScreen: ScreenManager("", R.string.full_image_screen)
}