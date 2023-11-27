package com.anpe.coolbbsyou.intent.state

import androidx.paging.PagingData
import com.anpe.coolbbsyou.net.model.nofitication.Data
import kotlinx.coroutines.flow.Flow

sealed class NotificationState {
    object Idle: NotificationState()

    object Loading: NotificationState()

    data class Success(val pager: Flow<PagingData<Data>>): NotificationState()

    data class Error(val e: String): NotificationState()
}