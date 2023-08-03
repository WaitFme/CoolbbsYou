package com.anpe.coolbbsyou.data.intent

sealed class MainIntent {
    // 获取首页内容
    object GetIndex: MainIntent()

    // 获取帖子详情
    data class GetDetails(val id: Int): MainIntent()

    // 图片视图
    data class OpenNineGrid(val isNineGrid: Boolean): MainIntent()

    // 获取搜索推荐词
    data class GetSuggestSearch(val keyword: String): MainIntent()

    // 获取每日酷安
    data class GetTodayCool(val page: Int, val url: String): MainIntent()

    // 登陆
    data class LoginAccount(val requestHash: String, val account: String, val passwd: String, val captcha: String): MainIntent()

    // 登陆状态查询
    object LoginState: MainIntent()

    // 主页信息
    data class GetProfile(val uid: Int): MainIntent()

    // 获取消息
    object GetNotification: MainIntent()

    // 获取评论
    data class GetReply(val id: Int): MainIntent()

    // 点赞
    data class Like(val id: Int): MainIntent()

    // 取消点赞
    data class Unlike(val id: Int): MainIntent()

    // 关注
    data class Follow(val uid: Int): MainIntent()

    // 取消关注
    data class Unfollow(val uid: Int): MainIntent()
}