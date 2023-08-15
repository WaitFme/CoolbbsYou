package com.anpe.coolbbsyou.intent.state

sealed class IndexImageState {
    // 九宫格图片
    object NineGrid: IndexImageState()

    // 横滑图片排列
    object ImageRow: IndexImageState()
}