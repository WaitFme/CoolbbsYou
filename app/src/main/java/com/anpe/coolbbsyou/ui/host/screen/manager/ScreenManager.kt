package com.anpe.coolbbsyou.ui.host.screen.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R

sealed class ScreenManager(val route: String, @StringRes val resourceId: Int) {
    data object SplashScreen: ScreenManager("SplashScreen", R.string.splash_screen)

    data object MainScreen: ScreenManager("MainScreen", R.string.main_screen)

    data object LoginScreen: ScreenManager("LoginScreen", R.string.login_screen)

    data object ImageScreen: ScreenManager("ImageScreen", R.string.image_screen)

    data object PostScreen: ScreenManager("PostScreen", R.string.post_screen)

    data object SettingScreen: ScreenManager("SettingScreen", R.string.setting_screen)

    data object SpaceScreen: ScreenManager("SpaceScreen", R.string.space_screen)

    data object TopicScreen: ScreenManager("TopicScreen", R.string.topic_screen)

    data object SearchScreen: ScreenManager("SearchScreen", R.string.search_screen)

    data object NewsScreen: ScreenManager("NewsScreen", R.string.news_screen)
}