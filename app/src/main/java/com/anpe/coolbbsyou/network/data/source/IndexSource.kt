package com.anpe.coolbbsyou.network.data.source

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.network.data.model.index.Data
import com.anpe.coolbbsyou.network.data.repository.ApiRepository
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2

class IndexSource(context: Context): PagingSource<Int, Data>() {
    val sp: SharedPreferences
    val deviceCode: String
    val token: String
//    val deviceCode: String
    val installTime: String
    val firstItem: Int
    init {
        sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

        sp.getString("DEVICE_CODE", null).apply {
            deviceCode = if (this == null) {
                val code = TokenDeviceUtils.getDeviceCode(context)
                sp.edit().putString("DEVICE_CODE", code).apply()
                code
            } else {
                this
            }
        }

        firstItem = sp.getInt("FIRST_ITEM", 0)

        sp.getString("INSTALL_TIME", null).apply {
            installTime = if (this == null) {
                val timeStamp = System.currentTimeMillis().toString()
                sp.edit().putString("INSTALL_TIME", timeStamp).apply()
                timeStamp
            } else {
                this
            }
        }

        token = deviceCode.getTokenV2()
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentPage = params.key ?: 1
            val index = ApiRepository().getIndex(deviceCode, token, currentPage, 1, installTime, firstItem)
            val nextPage = currentPage + 1
            LoadResult.Page(index.data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}