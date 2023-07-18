package com.anpe.coolbbsyou.network.data.repository

import com.anpe.coolbbsyou.network.service.ApiService

class ApiRepository {
    private val api = ApiService.getSerVice()

    suspend fun getIndex(
            deviceCode: String,
            token: String,
            page: Int = 0,
            firstLauncher: Int,
            installTime: String,
            firstItem: Int,
            ids: String = ""
    ) = api.getIndex(
            device = deviceCode,
            token = token,
            page = page,
            firstLaunch = firstLauncher,
            installTime = installTime,
            firstItem = firstItem,
            ids = ids
    )

    suspend fun getDetails(deviceCode: String, token: String, id: Int) = api.getDetails(deviceCode = deviceCode, token = token, id = id)
}