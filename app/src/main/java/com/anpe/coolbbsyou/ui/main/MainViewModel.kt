package com.anpe.coolbbsyou.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.repository.ApiRepository
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.network.data.state.IndexState
import com.anpe.coolbbsyou.util.Utils.Companion.getTokenV2
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ApiRepository()

    val channel = Channel<MainIntent>(Channel.UNLIMITED)

    private val token: String = Constants.DEVICE_CODE.getTokenV2()

    private val _indexState = MutableStateFlow<IndexState>(IndexState.Idle)
    val indexState: StateFlow<IndexState> = _indexState

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val detailsState: StateFlow<DetailsState> = _detailsState

    private var firstItem = 0

    init {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetDetails -> getDetailsState(it.id)
                    MainIntent.GetIndex -> getIndexState()
                }
            }
        }
    }

    private fun getIndexState() {
        viewModelScope.launch {
            _indexState.emit(IndexState.Loading)
            _indexState.value = try {
                val success = IndexState.Success(repository.getIndex(token = token, firstLauncher = 1, firstItem = firstItem, page = 5))
                firstItem++
                success
            } catch (e: Exception) {
                IndexState.Error(e.localizedMessage ?: "error")
            }
        }
    }

    private fun getDetailsState(id: Int) {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Loading)
            _detailsState.value = try {
                DetailsState.Success(repository.getDetails(token = token, id = id))
            } catch (e: Exception) {
                DetailsState.Error(e.localizedMessage ?: "error")
            }
        }
    }
}