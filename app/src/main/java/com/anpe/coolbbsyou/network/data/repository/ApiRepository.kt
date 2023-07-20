package com.anpe.coolbbsyou.network.data.repository

import android.content.Context
import com.anpe.coolbbsyou.network.service.ApiService
import com.anpe.coolbbsyou.util.MyApplication
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2

class ApiRepository(val context: Context = MyApplication.context) {
    private val api = ApiService.getSerVice(context)

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
}