package com.anpe.coolbbsyou.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.domain.nofitication.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class NotificationSource(private val repository: RemoteRepository): PagingSource<Int, Data>() {
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