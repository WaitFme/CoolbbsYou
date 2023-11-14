package com.anpe.coolbbsyou.intent.state.replyDetail

import com.anpe.coolbbsyou.data.remote.domain.reply.ReplyModel

sealed class ReplyDetailState {
    data object Idle: ReplyDetailState()

    data object Loading: ReplyDetailState()

    data class Success(val replyModel: ReplyModel): ReplyDetailState()

    data class Error(val e: String): ReplyDetailState()
}