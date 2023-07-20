package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.today.TodayCoolEntity

sealed class TodayState {
    object Idle: TodayState()

    object Loading: TodayState()

    data class Success(val todayCoolEntity: TodayCoolEntity): TodayState()

    data class Error(val e: String): TodayState()
}