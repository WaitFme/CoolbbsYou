package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.net.model.loginInfo.LoginInfoModel

sealed class LoginInfoState {
    object Idle: LoginInfoState()

    object Loading: LoginInfoState()

    data class Success(val loginStateEntity: LoginInfoModel): LoginInfoState()

    data class Error(val e: String): LoginInfoState()
}