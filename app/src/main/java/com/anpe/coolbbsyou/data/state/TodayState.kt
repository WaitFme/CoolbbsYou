package com.anpe.coolbbsyou.data.state

import com.anpe.coolbbsyou.data.domain.today.TodayCoolEntity

sealed class TodayState {
    object Idle: TodayState()

    object Loading: TodayState()

    data class Success(val todayCoolEntity: TodayCoolEntity): TodayState()

    data class Error(val e: String): TodayState()
}