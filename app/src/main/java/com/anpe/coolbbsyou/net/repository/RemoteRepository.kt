package com.anpe.coolbbsyou.net.repository

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.net.service.Api2Service
import com.anpe.coolbbsyou.net.service.ApiService
import com.anpe.coolbbsyou.net.service.ApiServiceTwo
import com.anpe.coolbbsyou.net.service.LoginService
import com.anpe.coolbbsyou.util.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.ResponseBody
import retrofit2.Response

class RemoteRepository(context: Context = MyApplication.context) {
    private val sp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)

    private val api = ApiService.getSerVice(context)
    private val api2 = Api2Service.getService(context)
    private val apiLogin = LoginService.getService(context)
    private val apiCall = ApiServiceTwo.getSerVice(context)

    private val installTime: String = sp.getString("INSTALL_TIME", System.currentTimeMillis().toString())!!

    // index
    private var firstItem: Int? = null
    private var lastItem: Int? = null

    suspend fun getIndex(page: Int) = api2.getIndex(
        page = page,
        firstItem = firstItem,
        lastItem = lastItem,
        installTime = installTime,
    ).apply {
        val first = data[data.indexOfFirst { it.entityType == "feed" }].id
        val last = data[data.indexOfLast { it.entityType == "feed" }].id

        if (page == 1) {
            firstItem = first
        }

        lastItem = last
    }

    suspend fun getRequestHash() = CoroutineScope(Dispatchers.IO).async {
        apiCall.getRequestHash().execute() as Response<ResponseBody>
    }.await()

    suspend fun getDetails(id: Int) = api2.getDetails(id = id)

    suspend fun getTodayCool(page: Int, url: String) = api.getTodayCool(page = page, url = url)

    suspend fun getSuggestSearch(keyword: String) = api.getSuggestSearch(searchValue = keyword)

    suspend fun getSearch(keyword: String, page: Int) =
        api.getSearch(searchValue = keyword, page = page)

    suspend fun postAccount(requestHash: String, login: String, password: String) =
        apiLogin.postAccount(
            requestHash = requestHash,
            login = login,
            password = password,
        )

    suspend fun getNotification(page: Int) = api.getNotification(page = page)

    suspend fun getLoginInfo() = api.getLoginInfo()

    suspend fun getProfile(uid: Int) = api.getProfile(uid = uid)

    suspend fun getReply(
        id: Int,
        page: Int,
        listType: String = "lastupdate_desc",
        discussMode: Int = 1,
        feedType: String = "feed"
    ) = api2.getReply(
        id = id,
        page = page,
        listType = listType,
        discussMode = discussMode,
        feedType = feedType
    )

    suspend fun getLike(id: Int) = api.getLike(id = id)

    suspend fun getUnlike(id: Int) = api.getUnlike(id = id)

    suspend fun getFollow(uid: Int) = api.follow(uid)

    /**
     * 取消关注
     * @param uid 用户ID
     */
    suspend fun getUnFollow(uid: Int) = api.unFollow(uid)

    suspend fun createFeed(message: String, type: String = "feed", isPrivate: Boolean = false) =
        api.createFeed(
            message = message,
            type = type,
            status = if (isPrivate) -1 else 1
        )

    suspend fun getSpace(uid: Int) = api.space(uid)

    suspend fun getTopic(tag: String) = api.topic(tag)

    suspend fun getProduct(id: Int) = api.product(id)

    suspend fun getFeedList(uid: Int, page: Int, isIncludeTop: Boolean = true) =
        api.feedList(uid, page, if (isIncludeTop) 1 else 0)
}