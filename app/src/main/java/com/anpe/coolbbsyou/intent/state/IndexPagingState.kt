package com.anpe.coolbbsyou.intent.state

import androidx.paging.PagingData
import com.anpe.coolbbsyou.net.model.index.Data
import kotlinx.coroutines.flow.Flow

sealed class IndexPagingState {
    object Idle: IndexPagingState()

    object Loading: IndexPagingState()

    object LoadMore: IndexPagingState()

    data class Success(val pager: Flow<PagingData<Data>>): IndexPagingState()

    data class Error(val error: String): IndexPagingState()
}