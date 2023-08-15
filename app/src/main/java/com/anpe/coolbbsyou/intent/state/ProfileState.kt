package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.data.remote.domain.profile.ProfileModel

sealed class ProfileState {
    object Idle: ProfileState()

    object Loading: ProfileState()

    data class Success(val profileModel: ProfileModel): ProfileState()

    data class Error(val e: String): ProfileState()
}