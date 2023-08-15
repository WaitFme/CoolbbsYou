package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.data.remote.domain.details.DetailsModel

sealed class DetailsState {
    object Idle: DetailsState()

    object Loading: DetailsState()

    data class Success(val detailsEntity: DetailsModel): DetailsState()

    data class Error(val e: String): DetailsState()
}