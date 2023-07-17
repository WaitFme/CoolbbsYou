package com.anpe.coolbbsyou.network.data.repository

import com.anpe.coolbbsyou.network.service.ApiService
import java.net.IDN

class ApiRepository {
    private val api = ApiService.getSerVice()

    // first item 47536152
    // install time 1689225425939
    suspend fun getIndex(
        page: Int = 1,
        firstLauncher: Int = 0,
        installTime: String? = "1689225425939",
        firstItem: Int,
        token: String
    ) = api.getIndex(
        token = token,
        page = page,
        firstLaunch = firstLauncher,
        installTime = installTime,
        firstItem = firstItem
    )

    suspend fun getDetails(token: String, id: Int) = api.getDetails(token = token, id = id)
}