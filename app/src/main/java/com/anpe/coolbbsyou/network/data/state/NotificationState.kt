package com.anpe.coolbbsyou.network.data.state

import androidx.paging.Pager
import com.anpe.coolbbsyou.network.data.model.nofitication.Data

sealed class NotificationState {
    object Idle: NotificationState()

    object Loading: NotificationState()

    data class Success(val pager: Pager<Int, Data>): NotificationState()

    data class Error(val e: String): NotificationState()
}