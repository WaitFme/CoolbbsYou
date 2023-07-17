package com.anpe.coolbbsyou.network.data.intent

import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity

sealed class MainIntent {
    object GetIndex: MainIntent()

    data class GetDetails(val id: Int): MainIntent()
}