package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.data.remote.domain.suggest.SuggestSearchModel

sealed class SuggestState {
    object Idle: SuggestState()

    object Loading: SuggestState()

    data class Success(val suggestSearchEntity: SuggestSearchModel): SuggestState()

    data class Error(val e: String): SuggestState()
}