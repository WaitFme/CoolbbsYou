package com.anpe.coolbbsyou.data.state

import com.anpe.coolbbsyou.data.domain.login.LoginEntity

sealed class LoginState {
    object Idle: LoginState()
    object LoggingIn: LoginState()
    data class Success(val loginEntity: LoginEntity): LoginState()
    data class Error(val error: String): LoginState()
}