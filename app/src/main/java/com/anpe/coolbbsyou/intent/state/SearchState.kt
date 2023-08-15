package com.anpe.coolbbsyou.intent.state

import androidx.paging.Pager
import com.anpe.coolbbsyou.data.remote.domain.search.Data

sealed class SearchState {
    object Idle: SearchState()

    object Loading: SearchState()

    data class Success(val data: Pager<Int, Data>): SearchState()

    data class Error(val e: String): SearchState()
}