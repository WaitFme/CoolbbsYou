package com.anpe.coolbbsyou.intent.state.global

sealed class GlobalState {
    data object NoInitial: GlobalState()

    data object Initialing: GlobalState()

    data class InitSuccess(
        val s: String
    ): GlobalState()
}

data class GlobalData(
    var isNineGrid: Boolean = false
)