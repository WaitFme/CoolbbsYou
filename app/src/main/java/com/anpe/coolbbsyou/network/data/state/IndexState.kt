package com.anpe.coolbbsyou.network.data.state

import androidx.paging.Pager
import com.anpe.coolbbsyou.network.data.model.index.Data

sealed class IndexState {
    object Idle: IndexState()

    object Loading: IndexState()

    data class Success(val pager: Pager<Int, Data>): IndexState()

    data class Error(val error: String): IndexState()
}