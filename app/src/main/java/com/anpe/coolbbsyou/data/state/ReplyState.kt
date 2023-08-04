package com.anpe.coolbbsyou.data.state

import androidx.paging.PagingData
import com.anpe.coolbbsyou.data.domain.reply.Data
import kotlinx.coroutines.flow.Flow

sealed class ReplyState {
    object Idle: ReplyState()

    object Loading: ReplyState()

    data class Success(val pager: Flow<PagingData<Data>>): ReplyState()

    data class Error(val e: String): ReplyState()
}