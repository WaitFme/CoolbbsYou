package com.anpe.coolbbsyou.intent.state

import androidx.paging.PagingData
import com.anpe.coolbbsyou.net.model.index.Data
import kotlinx.coroutines.flow.Flow

sealed class IndexState {
    data object Idle: IndexState()

    data object Loading: IndexState()

    data class Success(val pager: Flow<PagingData<Data>>): IndexState()

    data class Error(val error: String): IndexState()
}