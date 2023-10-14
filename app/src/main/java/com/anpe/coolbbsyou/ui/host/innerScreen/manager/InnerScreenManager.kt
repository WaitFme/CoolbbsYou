package com.anpe.coolbbsyou.ui.host.innerScreen.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R

sealed class InnerScreenManager(val route: String, @StringRes val resourceId: Int) {
    data object HomeInnerScreen: InnerScreenManager("HomeInnerScreen", R.string.home_inner_screen)

    data object TodaySelectionInnerScreen: InnerScreenManager("TodaySelectionScreen", R.string.today_selection_inner_screen)

    data object SearchInnerScreen: InnerScreenManager("SearchInnerScreen", R.string.search_inner_screen)

    data object UserSpaceInnerScreen: InnerScreenManager("UserSpaceInnerScreen", R.string.user_space_inner_screen)

    data object TopicInnerScreen: InnerScreenManager("TopicInnerScreen", R.string.topic_inner_screen)
}