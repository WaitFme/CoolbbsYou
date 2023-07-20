package com.anpe.coolbbsyou.ui.pager.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R

sealed class PagerManager(val route: String, @StringRes val resourceId: Int) {
    object HomePager: PagerManager("HomePager", R.string.home_pager)
    object MessagePager: PagerManager("MessagePager", R.string.message_pager)
    object SettingsPager: PagerManager("SettingsPager", R.string.settings_pager)
    object TodayCoolPager: PagerManager("TodayCoolPager", R.string.settings_pager)
}