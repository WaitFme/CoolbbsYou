package com.anpe.coolbbsyou.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.data.domain.reply.Data
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository

class ReplySource(private val repository: RemoteRepository, val id: Int): PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try{
            val currentPage = params.key ?: 1

            val replyEntity = repository.getReply(id = id, page = currentPage)

            val nextPage = currentPage + 1

            LoadResult.Page(replyEntity.data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}