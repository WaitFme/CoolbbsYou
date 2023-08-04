package com.anpe.coolbbsyou.data.state

import com.anpe.coolbbsyou.data.domain.suggest.SuggestSearchEntity

sealed class SuggestState {
    object Idle: SuggestState()

    object Loading: SuggestState()

    data class Success(val suggestSearchEntity: SuggestSearchEntity): SuggestState()

    data class Error(val e: String): SuggestState()
}