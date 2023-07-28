package com.anpe.coolbbsyou.network.data.repository

import android.content.Context
import com.anpe.coolbbsyou.network.data.model.index.IndexEntity
import com.anpe.coolbbsyou.network.service.ApiService
import com.anpe.coolbbsyou.network.service.ApiServiceTwo
import com.anpe.coolbbsyou.util.MyApplication
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getLastingInstallTime

class ApiRepository(context: Context = MyApplication.context) {
    private val api = ApiService.getSerVice(context)
    private val apiCall = ApiServiceTwo.getSerVice(context)

    private val installTime = getLastingInstallTime(context)

    private var firstLauncher = 1

    suspend fun getIndex(page: Int, lastItem: Int? = null): IndexEntity {
        val indexEntity = api.getIndex(
            page = page,
            lastItem = lastItem,
            firstLaunch = firstLauncher,
            installTime = installTime
        )

        if (firstLauncher == 1) {
            firstLauncher = 0
        }

        return indexEntity
    }

    fun getRequestHash() = apiCall.getRequestHash()

    suspend fun getDetails(id: Int) = api.getDetails(id = id)

    suspend fun getTodayCool(page: Int, url: String) = api.getTodayCool(page = page, url = url)

    suspend fun getSuggestSearch(keyword: String) = api.getSuggestSearch(searchValue = keyword)

    suspend fun postAccount(requestHash: String, login: String, password: String) = api.postAccount(
        requestHash = requestHash,
        login = login,
        password = password,
    )

    suspend fun getNotification(page: Int) = api.getNotification(page = page)

    suspend fun getLoginState() = api.getLoginState()

    suspend fun getProfile(uid: Int) = api.getProfile(uid = uid)

    suspend fun getReply(id: Int, page: Int, listType: String = "lastupdate_desc") =
        api.getReply(id = id, listType = listType, page = page)
}