package com.anpe.coolbbsyou.intent.event

sealed class MainEvent {
    // 获取首页内容
    data object GetIndex: MainEvent()

    // 获取帖子详情
    data class GetDetails(val id: Int): MainEvent()

    // 图片视图
    data class OpenNineGrid(val isNineGrid: Boolean): MainEvent()

    // 搜索
    data class GetSearch(val keyword: String): MainEvent()

    // 获取搜索推荐词
    data class GetSuggestSearch(val keyword: String): MainEvent()

    // 获取每日酷安
    data class GetTodayCool(val page: Int, val url: String): MainEvent()

    // 登陆
    data class LoginAccount(val requestHash: String, val account: String, val passwd: String, val captcha: String): MainEvent()

    // 退出登陆
    data object LogoutAccount: MainEvent()

    // 登陆状态查询
    data object GetLoginInfo: MainEvent()

    // 主页信息
    data class GetProfile(val uid: Int): MainEvent()

    data class GetSpace(val uid: Int): MainEvent()

    // 获取消息
    data object GetNotification: MainEvent()

    // 获取评论
    data class GetReply(val id: Int): MainEvent()

    // 获取评论详情
    data class GetReplyDetail(val id: Int): MainEvent()

    // 点赞
    data class Like(val id: Int): MainEvent()

    // 取消点赞
    data class Unlike(val id: Int): MainEvent()

    // 关注
    data class Follow(val uid: Int): MainEvent()

    // 取消关注
    data class Unfollow(val uid: Int): MainEvent()

    // 发布动态
    data class CreateFeed(val message: String): MainEvent()

    // 话题
    data class GetTopic(val topic: String): MainEvent()

    // 动态列表
    data class GetFeedList(val uid: Int, val page: Int): MainEvent()
}