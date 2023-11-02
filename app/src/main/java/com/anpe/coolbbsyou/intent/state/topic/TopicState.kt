package com.anpe.coolbbsyou.intent.state.topic

import com.anpe.coolbbsyou.data.remote.domain.topic.TopicModel

sealed class TopicState {
    data object Idle: TopicState()

    data object Loading: TopicState()

    data class Success(val topicModel: TopicModel): TopicState()

    data class Error(val e: String): TopicState()
}