package com.anpe.coolbbsyou.network.data.intent

sealed class MainIntent {
    // 获取首页内容
    object GetIndex: MainIntent()

    // 获取首页内容
    object LoadIndex: MainIntent()

    // 获取帖子详情
    data class GetDetails(val id: Int): MainIntent()

    // 图片视图
    data class OpenNineGrid(val isNineGrid: Boolean): MainIntent()

    // 获取搜索推荐词
    data class GetSuggestSearch(val keyword: String): MainIntent()

    // 获取每日酷安
    data class GetTodayCool(val page: Int, val url: String): MainIntent()
}