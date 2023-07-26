package com.anpe.coolbbsyou.network.data.state

import com.anpe.coolbbsyou.network.data.model.nofitication.NotificationEntity

sealed class NotificationState {
    object Idle: NotificationState()

    object Loading: NotificationState()

    data class Success(val notificationEntity: NotificationEntity): NotificationState()

    data class Error(val e: String): NotificationState()
}