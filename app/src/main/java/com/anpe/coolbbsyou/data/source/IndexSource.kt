package com.anpe.coolbbsyou.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.domain.index.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class IndexSource(private val repository: RemoteRepository) : PagingSource<Int, Data>() {
    companion object {
        private val TAG = IndexSource::class.java.simpleName
    }

    private var lastItem = -1

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        Log.d(TAG, "getRefreshKey: $state")
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentPage = params.key ?: 1

            Log.d(TAG, "load0: $lastItem")
            val indexEntity = if (lastItem == -1) {
                val indexEntity = repository.getIndex(currentPage)
                lastItem = indexEntity.data[indexEntity.data.lastIndex - 1].id
                Log.d(TAG, "load0-0: $lastItem")
                indexEntity
            } else {
                val indexEntity = RemoteRepository().getIndex(currentPage, lastItem)
                lastItem = indexEntity.data.last().id
                Log.d(TAG, "load0-1: $lastItem")
                indexEntity
            }

            val nextPage = if (currentPage == 10) {
                null
            } else {
                currentPage + 1
            }

            LoadResult.Page(indexEntity.data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}