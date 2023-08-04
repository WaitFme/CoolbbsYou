package com.anpe.coolbbsyou.data.state

import com.anpe.coolbbsyou.data.domain.loginState.LoginStateEntity

sealed class LoginInfoState {
    object Idle: LoginInfoState()

    object Loading: LoginInfoState()

    data class Success(val loginStateEntity: LoginStateEntity): LoginInfoState()

    data class Error(val e: String): LoginInfoState()
}