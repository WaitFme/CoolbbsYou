package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.data.remote.domain.loginState.LoginStateModel

sealed class LoginInfoState {
    object Idle: LoginInfoState()

    object Loading: LoginInfoState()

    data class Success(val loginStateEntity: LoginStateModel): LoginInfoState()

    data class Error(val e: String): LoginInfoState()
}