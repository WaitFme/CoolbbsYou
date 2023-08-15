package com.anpe.coolbbsyou.data.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.remote.domain.search.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class SearchPaging(private val repository: RemoteRepository, private val keyword: String): PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentPage = params.key ?: 1

            val searchModel = repository.getSearch(keyword = keyword, page = currentPage)

            val nextPage = currentPage + 1

            LoadResult.Page(searchModel.data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}