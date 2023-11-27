package com.anpe.coolbbsyou.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anpe.coolbbsyou.net.model.reply.Data
import com.anpe.coolbbsyou.net.repository.RemoteRepository

class ReplySource(
    private val repository: RemoteRepository,
    private val id: Int,
    private val feedType: String,
    private val listType: String,
    private val discussMode: Int,
): PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try{
            val currentPage = params.key ?: 1

            val replyEntity = repository.getReply(
                id = id,
                page = currentPage,
                listType = listType,
                discussMode = discussMode,
                feedType = feedType
            )

            val nextPage = currentPage + 1

            LoadResult.Page(replyEntity.data, null, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}