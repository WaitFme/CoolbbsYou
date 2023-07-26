package com.anpe.coolbbsyou.network.data.repository

import android.content.Context
import com.anpe.coolbbsyou.network.service.ApiService
import com.anpe.coolbbsyou.network.service.ApiServiceTwo
import com.anpe.coolbbsyou.util.MyApplication
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2

class ApiRepository(val context: Context = MyApplication.context) {
    private val api = ApiService.getSerVice(context)
    private val apiCall = ApiServiceTwo.getSerVice(context)

    private val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    private val deviceCode: String

    private val token: String

    private val installTime: String

    init {
        sp.getString("DEVICE_CODE", null).apply {
            deviceCode = if (this == null) {
                val code = TokenDeviceUtils.getDeviceCode(context)
                sp.edit().putString("DEVICE_CODE", code).apply()
                code
            } else {
                this
            }
        }

        token = deviceCode.getTokenV2()

//        println(deviceCode)
//        println(token)

        sp.getString("INSTALL_TIME", null).apply {
            installTime = if (this == null) {
                val timeStamp = System.currentTimeMillis().toString()
                sp.edit().putString("INSTALL_TIME", timeStamp).apply()
                timeStamp
            } else {
                this
            }
        }
    }

    suspend fun getIndex(page: Int, firstLauncher: Int, firstItem: Int) =
        api.getIndex(
            device = deviceCode,
            token = token,
            page = page,
            firstLaunch = firstLauncher,
            installTime = installTime,
            firstItem = firstItem
        )

    suspend fun getDetails(id: Int) =
        api.getDetails(deviceCode = deviceCode, token = token, id = id)

    suspend fun getTodayCool(page: Int, url: String) =
        api.getTodayCool(deviceCode = deviceCode, token = token, page = page, url = url)

    suspend fun getSuggestSearch(keyword: String) =
        api.getSuggestSearch(deviceCode = deviceCode, token = token, searchValue = keyword)

    fun getRequestHash() = apiCall.getRequestHash()

    suspend fun postAccount(
        requestHash: String,
        login: String,
        password: String,
        captcha: String = "",
        code: String = "",
    ) = api.postAccount(
        requestHash = requestHash,
        login = login,
        password = password,
        captcha = captcha,
        code = code
    )

    suspend fun getNotification() = api.getNotification(device = deviceCode, token = token)

    suspend fun getLoginState() = api.getLoginState(device = deviceCode, token = token)

    suspend fun getProfile(uid: Int) = api.getProfile(device = deviceCode, token = token, uid = uid)
}