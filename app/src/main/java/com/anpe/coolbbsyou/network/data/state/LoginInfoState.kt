package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.loginState.LoginStateEntity

sealed class LoginInfoState {
    object Idle: LoginInfoState()

    object Loading: LoginInfoState()

    data class Success(val loginStateEntity: LoginStateEntity): LoginInfoState()

    data class Error(val e: String): LoginInfoState()
}