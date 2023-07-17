package com.anpe.coolbbsyou.network.data.intent

import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity

sealed class MainIntent {
    // 获取首页内容
    object GetIndex: MainIntent()

    // 获取帖子详情
    data class GetDetails(val id: Int): MainIntent()

    // 图片视图
    data class OpenNineGrid(val isNineGrid: Boolean): MainIntent()
}