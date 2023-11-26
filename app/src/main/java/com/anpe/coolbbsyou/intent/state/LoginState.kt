package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.net.model.login.LoginModel

sealed class LoginState {
    object Idle: LoginState()
    object LoggingIn: LoginState()
    data class Success(val loginModel: LoginModel): LoginState()
    data class Error(val error: String): LoginState()
}