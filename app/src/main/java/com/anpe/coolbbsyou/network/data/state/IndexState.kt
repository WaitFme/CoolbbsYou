package com.anpe.coolbbsyou.network.data.state

import androidx.paging.PagingData
import com.anpe.coolbbsyou.network.data.model.index.Data
import kotlinx.coroutines.flow.Flow

sealed class IndexState {
    object Idle: IndexState()

    object Loading: IndexState()

    data class Success(val pager: Flow<PagingData<Data>>): IndexState()

    data class Error(val error: String): IndexState()
}