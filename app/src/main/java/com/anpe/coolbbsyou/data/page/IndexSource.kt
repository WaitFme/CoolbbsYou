package com.anpe.coolbbsyou.data.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.remote.domain.index.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class IndexSource(private val repository: RemoteRepository) : PagingSource<Int, Data>() {
    companion object {
        private val TAG = IndexSource::class.java.simpleName
    }

    //    private var lastItem = -1
    private var lastItem: Int? = null

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        Log.d(TAG, "getRefreshKey: $state")
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentPage = params.key ?: 1

            /*val indexEntity = if (lastItem == -1) {
                repository.getIndex(currentPage).apply {
                    lastItem = data[data.lastIndex - 1].id
                }
            } else {
                repository.getIndex(currentPage, lastItem).apply {
                    lastItem = data.last().id
                }
            }*/

            val indexEntity = repository.getIndex(currentPage, lastItem).apply {
                lastItem = data[if (lastItem == null) data.lastIndex - 1 else data.lastIndex].id
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