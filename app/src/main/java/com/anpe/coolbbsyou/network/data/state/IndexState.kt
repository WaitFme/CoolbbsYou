package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.index.IndexEntity

sealed class IndexState {
    object Idle: IndexState()

    object Loading: IndexState()

    data class Success(val indexEntity: IndexEntity): IndexState()

    data class Error(val error: String): IndexState()
}