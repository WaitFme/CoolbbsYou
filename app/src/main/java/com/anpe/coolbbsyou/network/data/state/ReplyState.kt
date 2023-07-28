package com.anpe.coolbbsyou.network.data.state

import androidx.paging.Pager
import com.anpe.coolbbsyou.network.data.model.reply.Data

sealed class ReplyState {
    object Idle: ReplyState()

    object Loading: ReplyState()

    data class Success(val pager: Pager<Int, Data>): ReplyState()

    data class Error(val e: String): ReplyState()
}