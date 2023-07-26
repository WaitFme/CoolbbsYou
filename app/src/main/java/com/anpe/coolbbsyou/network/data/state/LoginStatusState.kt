package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.loginState.LoginStateEntity

sealed class LoginStatusState {
    object Idle: LoginStatusState()

    object Loading: LoginStatusState()

    data class Success(val loginStateEntity: LoginStateEntity): LoginStatusState()

    data class Error(val e: String): LoginStatusState()
}