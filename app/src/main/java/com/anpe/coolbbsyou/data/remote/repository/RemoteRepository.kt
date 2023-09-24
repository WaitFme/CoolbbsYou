package com.anpe.coolbbsyou.data.remote.repository

import android.content.Context
import com.anpe.coolbbsyou.data.remote.service.ApiService
import com.anpe.coolbbsyou.data.remote.service.ApiServiceTwo
import com.anpe.coolbbsyou.util.LoginUtils.Companion.getRequestHash
import com.anpe.coolbbsyou.util.MyApplication
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getLastingInstallTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RemoteRepository(context: Context = MyApplication.context) {
    private val api = ApiService.getSerVice(context)
    private val apiCall = ApiServiceTwo.getSerVice(context)

    // index
    private var firstItem: Int? = null
    private var lastItem: Int? = null
    private val installTime = getLastingInstallTime(context)

    suspend fun getIndex(page: Int) = api.getIndex(
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

    suspend fun getRequestHash() = CoroutineScope(Dispatchers.Default).async {
        apiCall.getRequestHash().execute()
    }.await().body()?.string()?.getRequestHash()

    suspend fun getDetails(id: Int) = api.getDetails(id = id)

    suspend fun getTodayCool(page: Int, url: String) = api.getTodayCool(page = page, url = url)

    suspend fun getSuggestSearch(keyword: String) = api.getSuggestSearch(searchValue = keyword)

    suspend fun getSearch(keyword: String, page: Int) = api.getSearch(searchValue = keyword, page = page)

    suspend fun postAccount(requestHash: String, login: String, password: String) = api.postAccount(
        requestHash = requestHash,
        login = login,
        password = password,
    )

    suspend fun getNotification(page: Int) = api.getNotification(page = page)

    suspend fun getLoginState() = api.getLoginState()

    suspend fun getProfile(uid: Int) = api.getProfile(uid = uid)

    suspend fun getReply(id: Int, page: Int, listType: String = "lastupdate_desc") = api.getReply(id = id, listType = listType, page = page)

    suspend fun getLike(id: Int) = api.getLike(id = id)

    suspend fun getUnlike(id: Int) = api.getUnlike(id = id)
}