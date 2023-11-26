package com.anpe.coolbbsyou.intent.state

import com.anpe.coolbbsyou.net.model.today.TodayCoolModel

sealed class TodayState {
    object Idle: TodayState()

    object Loading: TodayState()

    data class Success(val todayCoolEntity: TodayCoolModel): TodayState()

    data class Error(val e: String): TodayState()
}