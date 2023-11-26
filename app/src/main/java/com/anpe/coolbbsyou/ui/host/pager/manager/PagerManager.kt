package com.anpe.coolbbsyou.ui.host.pager.manager

import androidx.annotation.StringRes
import com.anpe.coolbbsyou.R

sealed class PagerManager(val route: String, @StringRes val resourceId: Int) {
    data object HomePager: PagerManager("HomePager", R.string.home_pager)

    data object MessagePager: PagerManager("MessagePager", R.string.message_pager)

    data object MyPager: PagerManager("MyPager", R.string.my_pager)
}