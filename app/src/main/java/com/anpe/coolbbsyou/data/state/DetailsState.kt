package com.anpe.coolbbsyou.data.state

import com.anpe.coolbbsyou.data.domain.details.DetailsEntity

sealed class DetailsState {
    object Idle: DetailsState()

    object Loading: DetailsState()

    data class Success(val detailsEntity: DetailsEntity): DetailsState()

    data class Error(val e: String): DetailsState()
}