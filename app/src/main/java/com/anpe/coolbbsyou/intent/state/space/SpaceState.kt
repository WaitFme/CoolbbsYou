package com.anpe.coolbbsyou.intent.state.space

import com.anpe.coolbbsyou.net.model.space.SpaceModel

sealed class SpaceState {
    data object Idle: SpaceState()

    data object Loading: SpaceState()

    data class Success(val spaceModel: SpaceModel): SpaceState()

    data class Error(val e: String): SpaceState()
}