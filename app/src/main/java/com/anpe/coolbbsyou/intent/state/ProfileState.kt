package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.net.model.profile.ProfileModel

sealed class ProfileState {
    data object Idle: ProfileState()

    data object Loading: ProfileState()

    data class UnLogin(val message: String): ProfileState()

    data class Success(val profileModel: ProfileModel): ProfileState()

    data class Error(val e: String): ProfileState()
}