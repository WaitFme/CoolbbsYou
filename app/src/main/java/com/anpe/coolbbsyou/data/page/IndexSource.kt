package com.anpe.coolbbsyou.data.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.remote.domain.index.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class IndexSource(private val repository: RemoteRepository) : PagingSource<Int, Data>() {
    companion object {
        private val TAG = IndexSource::class.simpleName
    }

    private var firstItem: Int? = null
    private var lastItem: Int? = null

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        Log.d(TAG, "getRefreshKey: ${state.anchorPosition}")
//        firstItem = 1
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val currentPage = params.key ?: 1

        Log.d(TAG, "load: $currentPage $firstItem")

        val indexModel = repository.getIndex(
            page = currentPage,
            firstItem = firstItem,
            lastItem = lastItem
        ).apply {
            firstItem = if (currentPage == 1)
                data[data.indexOfFirst { it.entityType == "feed" }].id
            else null

            lastItem = data[data.indexOfLast {
                it.entityType == "feed"
            }].id
        }

        val nextPage = currentPage + 1

        return LoadResult.Page(indexModel.data, null, nextPage)
    }
}