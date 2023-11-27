package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.net.model.detail.DetailModel

sealed class DetailsState {
    object Idle: DetailsState()

    object Loading: DetailsState()

    data class Success(val detailsEntity: DetailModel): DetailsState()

    data class Error(val e: String): DetailsState()
}