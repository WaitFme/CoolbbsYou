package com.anpe.coolbbsyou.data.page

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.anpe.coolbbsyou.data.remote.domain.index.Data

@OptIn(ExperimentalPagingApi::class)
class IndexPaging: RemoteMediator<Int, Data>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Data>): MediatorResult {
        TODO("Not yet implemented")
    }
}