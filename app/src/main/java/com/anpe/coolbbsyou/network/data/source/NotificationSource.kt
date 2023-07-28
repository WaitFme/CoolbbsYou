package com.anpe.coolbbsyou.network.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.network.data.model.nofitication.Data
import com.anpe.coolbbsyou.network.data.repository.ApiRepository

class NotificationSource(private val repository: ApiRepository): PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentPage = params.key ?: 1

            val notificationEntity = repository.getNotification(currentPage)

            val nextPage = if (notificationEntity.data.isEmpty()) {
                null
            } else {
                currentPage + 1
            }

            LoadResult.Page(notificationEntity.data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}