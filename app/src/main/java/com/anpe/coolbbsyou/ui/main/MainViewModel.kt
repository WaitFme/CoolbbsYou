package com.anpe.coolbbsyou.ui.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.repository.ApiRepository
import com.anpe.coolbbsyou.network.data.source.IndexSource
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.network.data.state.IndexImageState
import com.anpe.coolbbsyou.network.data.state.IndexState
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getDeviceCode
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private val repository = ApiRepository()

    private val sp: SharedPreferences

//    private val context: Context

    val channel = Channel<MainIntent>(Channel.UNLIMITED)

    private val _indexState = MutableStateFlow<IndexState>(IndexState.Idle)
    val indexState: StateFlow<IndexState> = _indexState

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val detailsState: StateFlow<DetailsState> = _detailsState

    private val _indexImageState = MutableStateFlow<IndexImageState>(IndexImageState.ImageRow)
    val indexImageState: StateFlow<IndexImageState> = _indexImageState

    private val deviceCode: String

    private val token: String

    private val installTime: String

    private var firstItem: Int

    private var isFirstLauncher = 1

    private var pager = 0

    init {
        sp = application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)

        sp.getString("DEVICE_CODE", null).apply {
            deviceCode = if (this == null) {
                val code = getDeviceCode(application)
                sp.edit().putString("DEVICE_CODE", code).apply()
                code
            } else {
                this
            }
        }

        firstItem = sp.getInt("FIRST_ITEM", 0)

        sp.getString("INSTALL_TIME", null).apply {
            installTime = if (this == null) {
                val timeStamp = System.currentTimeMillis().toString()
                sp.edit().putString("INSTALL_TIME", timeStamp).apply()
                timeStamp
            } else {
                this
            }
        }

        isNineGrid(sp.getBoolean("IS_NINE_GRID", false))

        token = deviceCode.getTokenV2()

        getIndexState()

        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetDetails -> getDetailsState(it.id)
                    is MainIntent.OpenNineGrid -> isNineGrid(it.isNineGrid)
                    is MainIntent.LoadIndex -> {
                        pager++
                        getIndexState()
                    }
                    MainIntent.GetIndex -> getIndexState()
                }
            }
        }
    }

    private fun getIndexState() {
        viewModelScope.launch {
            _indexState.emit(IndexState.Loading)
            _indexState.value = try {
                val success = IndexState.Success(repository.getIndex(
                        deviceCode = deviceCode,
                        token = token,
                        page = pager,
                        firstLauncher = isFirstLauncher,
                        firstItem = firstItem,
                        installTime = installTime
                ))
                firstItem++
                sp.edit().putInt("FIRST_ITEM", firstItem).apply()
                if (isFirstLauncher == 1) {
                    isFirstLauncher = 0
                }
                success
            } catch (e: Exception) {
                IndexState.Error(e.localizedMessage ?: "error")
            }
        }
    }

    fun getData() = Pager(PagingConfig(pageSize = 1)) {
        IndexSource(application)
    }.flow

    private fun getLoadIndexState() {
        viewModelScope.launch {
            _indexState.emit(IndexState.Loading)
            _indexState.tryEmit(IndexState.Loading)
            _indexState.value = try {
                val success = IndexState.Success(repository.getIndex(
                        deviceCode = deviceCode,
                        token = token,
                        page = pager,
                        firstLauncher = isFirstLauncher,
                        firstItem = firstItem,
                        installTime = installTime
                ))
                firstItem++
                sp.edit().putInt("FIRST_ITEM", firstItem).apply()
                if (isFirstLauncher == 0) {
                    isFirstLauncher = 1
                }
                success
            } catch (e: Exception) {
                IndexState.Error(e.localizedMessage ?: "error")
            }
        }
    }

    private fun isNineGrid(isNineGrid: Boolean) {
        viewModelScope.launch {
            if (isNineGrid) {
                _indexImageState.emit(IndexImageState.NineGrid)
            } else {
                _indexImageState.emit(IndexImageState.ImageRow)
            }
            sp.edit().putBoolean("IS_NINE_GRID", isNineGrid).apply()
        }
    }

    private fun getDetailsState(id: Int) {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Loading)
            _detailsState.value = try {
                DetailsState.Success(repository.getDetails(deviceCode = deviceCode, token = token, id = id))
            } catch (e: Exception) {
                DetailsState.Error(e.localizedMessage ?: "error")
            }
        }
    }
}