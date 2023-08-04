package com.anpe.coolbbsyou.ui.host.innerScreen.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager

sealed class InnerScreenManager(val route: String, @StringRes val resourceId: Int) {
    object HomeInnerScreen: InnerScreenManager("HomeInnerScreen", R.string.home_inner_screen)

    object DetailsInnerScreen: InnerScreenManager("DetailsInnerScreen", R.string.details_inner_screen)

    object TodaySelectionInnerScreen: InnerScreenManager("TodaySelectionScreen", R.string.today_selection_inner_screen)
}