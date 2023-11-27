package com.anpe.coolbbsyou.ui.theme

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.sp

class FSize(private val windowSizeClass: WindowSizeClass) {
    val titleSize = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> { 13.sp }
        WindowWidthSizeClass.Medium -> { 13.sp }
        WindowWidthSizeClass.Compact -> { 13.sp }
        else -> { 13.sp }
    }

    val contentSize = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> { 13.sp }
        WindowWidthSizeClass.Medium -> { 13.sp }
        WindowWidthSizeClass.Compact -> { 13.sp }
        else -> { 13.sp }
    }

    val subTitleSize = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> { 13.sp }
        WindowWidthSizeClass.Medium -> { 13.sp }
        WindowWidthSizeClass.Compact -> { 13.sp }
        else -> { 13.sp }
    }
}