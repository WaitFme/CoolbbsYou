package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity

sealed class DetailsState {
    object Idle: DetailsState()

    object Loading: DetailsState()

    data class Success(val detailsEntity: DetailsEntity): DetailsState()

    data class Error(val e: String): DetailsState()
}