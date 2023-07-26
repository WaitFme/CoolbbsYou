package com.anpe.coolbbsyou.ui.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.loginState.LoginStateEntity
import com.anpe.coolbbsyou.network.data.model.profile.ProfileEntity
import com.anpe.coolbbsyou.network.data.repository.ApiRepository
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.network.data.state.IndexImageState
import com.anpe.coolbbsyou.network.data.state.IndexState
import com.anpe.coolbbsyou.network.data.state.LoginState
import com.anpe.coolbbsyou.network.data.state.LoginStatusState
import com.anpe.coolbbsyou.network.data.state.NotificationState
import com.anpe.coolbbsyou.network.data.state.ProfileState
import com.anpe.coolbbsyou.network.data.state.SuggestState
import com.anpe.coolbbsyou.network.data.state.TodayState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private val repository = ApiRepository()

    private val sp: SharedPreferences =
        application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)

    val channel = Channel<MainIntent>(Channel.UNLIMITED)

    private val _indexState = MutableStateFlow<IndexState>(IndexState.Idle)
    val indexState: StateFlow<IndexState> = _indexState

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val detailsState: StateFlow<DetailsState> = _detailsState

    private val _indexImageState = MutableStateFlow<IndexImageState>(IndexImageState.ImageRow)
    val indexImageState: StateFlow<IndexImageState> = _indexImageState

    private val _suggestState = MutableStateFlow<SuggestState>(SuggestState.Idle)
    val suggestState: StateFlow<SuggestState> = _suggestState

    private val _todayState = MutableStateFlow<TodayState>(TodayState.Idle)
    val todayState: StateFlow<TodayState> = _todayState

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _loginStatusState = MutableStateFlow<LoginStatusState>(LoginStatusState.Idle)
    val loginStatusState: StateFlow<LoginStatusState> = _loginStatusState

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _notificationState = MutableStateFlow<NotificationState>(NotificationState.Idle)
    val notificationState: StateFlow<NotificationState> = _notificationState

    private var firstItem: Int = sp.getInt("FIRST_ITEM", 0)

    private var isFirstLauncher = 1

    private var pager = 0

    init {
        isNineGrid(sp.getBoolean("IS_NINE_GRID", false))

        getLoginStatus()

        getIndexState()

        channelHandler(channel)
    }

    fun sendIntent(intent: MainIntent) {
        viewModelScope.launch {
            channel.send(intent)
        }
    }

    private fun channelHandler(channel: Channel<MainIntent>) {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetIndex -> getIndexState()
                    is MainIntent.LoadIndex -> { }
                    is MainIntent.GetDetails -> getDetailsState(it.id)
                    is MainIntent.OpenNineGrid -> isNineGrid(it.isNineGrid)
                    is MainIntent.GetSuggestSearch -> getSuggestSearch(it.keyword)
                    is MainIntent.GetTodayCool -> getTodayCool(it.url, it.page)
                    is MainIntent.LoginAccount -> postAccount(it.account, it.passwd, it.requestHash)
                    is MainIntent.LoginState -> getLoginStatus()
                    is MainIntent.GetProfile -> getProfile(it.uid)
                    is MainIntent.GetNotification -> getNotification()
                }
            }
        }
    }

    private fun getIndexState() {
        viewModelScope.launch {
            _indexState.emit(IndexState.Loading)
            _indexState.value = try {
                val success = IndexState.Success(repository.getIndex(
                        page = pager,
                        firstLauncher = isFirstLauncher,
                        firstItem = firstItem
                ))
                firstItem++
                sp.edit().putInt("FIRST_ITEM", firstItem).apply()
                if (isFirstLauncher == 1) {
                    isFirstLauncher = 0
                }
                success
            } catch (e: Exception) {
                Log.e(TAG, "getIndexState: $e")
                println(e)
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
                DetailsState.Success(repository.getDetails(id = id))
            } catch (e: Exception) {
                DetailsState.Error(e.localizedMessage ?: "error")
            }
        }
    }

    private fun getSuggestSearch(keyword: String) {
        viewModelScope.launch {
            _suggestState.emit(SuggestState.Loading)
            _suggestState.value = try {
                SuggestState.Success(repository.getSuggestSearch(keyword = keyword))
            } catch (e: Exception) {
                SuggestState.Error(e.localizedMessage ?: "UNKNOWN ERROR")
            }
        }
    }

    private fun getTodayCool(url: String, page: Int) {
        viewModelScope.launch {
            _todayState.emit(TodayState.Loading)
            _todayState.value = try {
                TodayState.Success(repository.getTodayCool(page, url))
            } catch (e: Exception) {
                TodayState.Error(e.localizedMessage ?: "UNKNOWN")
            }
        }
    }

    private fun postAccount(account: String, passwd: String, requestHash: String) {
        viewModelScope.launch {
            _loginState.emit(LoginState.LoggingIn)
            _loginState.value = try {
                LoginState.Success(repository.postAccount(requestHash, account, passwd))
            } catch (e: Exception) {
                LoginState.Error(e.localizedMessage ?: "UNKNOWN")
            }
        }
    }

    private fun getLoginStatus() {
        viewModelScope.launch {
            _loginStatusState.emit(LoginStatusState.Loading)
            _loginStatusState.value = try {
                val loginStateEntity = repository.getLoginState()
                if (loginStateEntity.error == -1) {
                    sp.edit().putBoolean("isLogin", false).apply()
                    LoginStatusState.Error("请登陆")
                } else {
                    saveLoginStatus(loginStateEntity)
                    LoginStatusState.Success(loginStateEntity)
                }
            } catch (e: Exception) {
                LoginStatusState.Error(e.localizedMessage ?: "UNKNOWN")
            }
        }
    }

    private fun saveLoginStatus(loginStateEntity: LoginStateEntity) {
        loginStateEntity.data.apply {
            sp.edit().putInt("uid", uid.toInt()).apply()
            sp.edit().putBoolean("isLogin", true).apply()
        }
    }

    private fun getProfile(uid: Int) {
        viewModelScope.launch {
            _profileState.emit(ProfileState.Loading)
            _profileState.value = try {
                val profileEntity = repository.getProfile(uid)
                saveProfile(profileEntity)
                ProfileState.Success(profileEntity)
            } catch (e: Exception) {
                ProfileState.Error(e.localizedMessage ?: "UNKNOWN")
            }
        }
    }

    private fun saveProfile(profileEntity: ProfileEntity) {
        profileEntity.data.apply {
            sp.edit().putInt("uid", uid).apply()
            sp.edit().putString("username", username).apply()
            sp.edit().putInt("level", level).apply()
            sp.edit().putInt("experience", experience).apply()
            sp.edit().putInt("next_level_experience", nextLevelExperience).apply()
            sp.edit().putInt("feed", feed).apply()
            sp.edit().putInt("follow", follow).apply()
            sp.edit().putInt("fans", fans).apply()
            sp.edit().putString("next_level_percentage", nextLevelPercentage).apply()
            sp.edit().putString("userAvatar", userAvatar).apply()
        }
    }

    private fun getNotification() {
        viewModelScope.launch {
            _notificationState.emit(NotificationState.Loading)
            _notificationState.value = try {
                NotificationState.Success(repository.getNotification())
            } catch (e: Exception) {
                NotificationState.Error(e.localizedMessage ?: "UNKNOWN")
            }
        }
    }
}