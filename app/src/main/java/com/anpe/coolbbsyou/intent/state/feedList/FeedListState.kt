package com.anpe.coolbbsyou.intent.state.feedList

import com.anpe.coolbbsyou.net.model.feedList.FeedListModel

sealed class FeedListState {
    data object Idle: FeedListState()

    data object Loading: FeedListState()

    data class Success(val feedListModel: FeedListModel): FeedListState()

    data class Error(val e: String): FeedListState()
}