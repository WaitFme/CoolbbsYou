package com.anpe.coolbbsyou.data.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.remote.domain.index.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class IndexSource(private val repository: RemoteRepository) : PagingSource<Int, Data>() {
    companion object {
        private val TAG = IndexSource::class.simpleName
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val currentPage = params.key ?: 1

        return try {
            val indexModel = repository.getIndex(currentPage)

            val nextPage = currentPage + 1

            LoadResult.Page(indexModel.data, null, nextPage)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}