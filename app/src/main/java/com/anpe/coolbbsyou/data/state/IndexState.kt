package com.anpe.coolbbsyou.data.state

import androidx.paging.PagingData
import com.anpe.coolbbsyou.data.domain.index.Data
import kotlinx.coroutines.flow.Flow

sealed class IndexState {
    object Idle: IndexState()

    object Loading: IndexState()

    data class Success(val pager: Flow<PagingData<Data>>): IndexState()

    data class Error(val error: String): IndexState()
}