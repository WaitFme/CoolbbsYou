package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.profile.ProfileEntity

sealed class ProfileState {
    object Idle: ProfileState()

    object Loading: ProfileState()

    data class Success(val profileEntity: ProfileEntity): ProfileState()

    data class Error(val e: String): ProfileState()
}