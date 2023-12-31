package com.anpe.coolbbsyou.ui.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.anpe.coolbbsyou.network.data.intent.MainIntent
import com.anpe.coolbbsyou.network.data.model.profile.ProfileEntity
import com.anpe.coolbbsyou.network.data.repository.ApiRepository
import com.anpe.coolbbsyou.network.data.source.IndexSource
import com.anpe.coolbbsyou.network.data.source.NotificationSource
import com.anpe.coolbbsyou.network.data.source.ReplySource
import com.anpe.coolbbsyou.network.data.state.DetailsState
import com.anpe.coolbbsyou.network.data.state.IndexImageState
import com.anpe.coolbbsyou.network.data.state.IndexState
import com.anpe.coolbbsyou.network.data.state.LoginInfoState
import com.anpe.coolbbsyou.network.data.state.LoginState
import com.anpe.coolbbsyou.network.data.state.NotificationState
import com.anpe.coolbbsyou.network.data.state.ProfileState
import com.anpe.coolbbsyou.network.data.state.ReplyState
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

    private val _loginInfoState = MutableStateFlow<LoginInfoState>(LoginInfoState.Idle)
    val loginInfoState: StateFlow<LoginInfoState> = _loginInfoState

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _notificationState = MutableStateFlow<NotificationState>(NotificationState.Idle)
    val notificationState: StateFlow<NotificationState> = _notificationState

    private val _replyState = MutableStateFlow<ReplyState>(ReplyState.Idle)
    val replyState: StateFlow<ReplyState> = _replyState

    private var isLogin = false

    init {
        isNineGrid(sp.getBoolean("IS_NINE_GRID", false))

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
                    is MainIntent.GetDetails -> getDetailsState(it.id)
                    is MainIntent.OpenNineGrid -> isNineGrid(it.isNineGrid)
                    is MainIntent.GetSuggestSearch -> getSuggestSearch(it.keyword)
                    is MainIntent.GetTodayCool -> getTodayCool(it.url, it.page)
                    is MainIntent.LoginAccount -> postAccount(it.account, it.passwd, it.requestHash)
                    is MainIntent.LoginState -> getLoginInfo()
                    is MainIntent.GetProfile -> getProfile(it.uid)
                    is MainIntent.GetNotification -> getNotification()
                    is MainIntent.GetReply -> getReply(it.id)
                }
            }
        }
    }

    /**
     * 更新获取首页推荐状态流
     */
    private fun getIndexState() {
        viewModelScope.launch {
            _indexState.emit(IndexState.Loading)
            _indexState.emit(
                try {
                    IndexState.Success(Pager(
                        PagingConfig(pageSize = 50, prefetchDistance = 10),
                        pagingSourceFactory = {
                            IndexSource(repository)
                        }
                    ).flow.cachedIn(viewModelScope))
                } catch (e: Exception) {
                    Log.e(TAG, "getIndexState: $e")
                    IndexState.Error(e.localizedMessage ?: "error")
                }
            )
        }
    }

    /**
     * 首页推荐信息流图片排列方式
     * @param isNineGrid true: 九宫格排列; false: 横向列表排列
     */
    private fun isNineGrid(isNineGrid: Boolean) {
        viewModelScope.launch {
            _indexImageState.emit(
                if (isNineGrid) {
                    IndexImageState.NineGrid
                } else {
                    IndexImageState.ImageRow
                }
            )
            sp.edit().putBoolean("IS_NINE_GRID", isNineGrid).apply()
        }
    }

    /**
     * 信息流详情页状态流
     * @param id 信息流ID
     */
    private fun getDetailsState(id: Int) {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Loading)
            _detailsState.emit(
                try {
                    DetailsState.Success(repository.getDetails(id = id))
                } catch (e: Exception) {
                    DetailsState.Error(e.localizedMessage ?: "error")
                }
            )
        }
    }

    /**
     * 搜索推荐状态流
     * @param keyword 搜索关键字
     */
    private fun getSuggestSearch(keyword: String) {
        viewModelScope.launch {
            _suggestState.emit(SuggestState.Loading)
            _suggestState.emit(try {
                SuggestState.Success(repository.getSuggestSearch(keyword = keyword))
            } catch (e: Exception) {
                SuggestState.Error(e.localizedMessage ?: "UNKNOWN ERROR")
            })
        }
    }

    /**
     * 今日酷安状态流
     * @param url 今日酷安路由地址
     * @param page 分页
     */
    private fun getTodayCool(url: String, page: Int) {
        viewModelScope.launch {
            _todayState.emit(TodayState.Loading)
            _todayState.emit(try {
                TodayState.Success(repository.getTodayCool(page, url))
            } catch (e: Exception) {
                TodayState.Error(e.localizedMessage ?: "UNKNOWN")
            })
        }
    }

    /**
     * 登陆账户状态流
     * @param account 账号
     * @param passwd 密码
     * @param requestHash 盐值
     */
    private fun postAccount(account: String, passwd: String, requestHash: String) {
        viewModelScope.launch {
            _loginState.emit(LoginState.LoggingIn)
            _loginState.emit(try {
                LoginState.Success(repository.postAccount(requestHash, account, passwd))
            } catch (e: Exception) {
                LoginState.Error(e.localizedMessage ?: "UNKNOWN")
            })
        }
    }

    /**
     * 登陆信息状态流
     */
    private fun getLoginInfo() {
        viewModelScope.launch {
            _loginInfoState.emit(LoginInfoState.Loading)
            _loginInfoState.emit(try {
                val loginStateEntity = repository.getLoginState()
                if (loginStateEntity.error == -1) {
                    isLogin = false
                    sp.edit().putBoolean("isLogin", isLogin).apply()
                    LoginInfoState.Error("请登陆")
                } else {
                    isLogin = true
                    sp.edit().putInt("uid", loginStateEntity.data.uid.toInt()).apply()
                    sp.edit().putBoolean("isLogin", isLogin).apply()
                    LoginInfoState.Success(loginStateEntity)
                }
            } catch (e: Exception) {
                LoginInfoState.Error(e.localizedMessage ?: "UNKNOWN")
            })
        }
    }

    /**
     * 个人主页信息状态流
     * @param uid 账户ID
     */
    private fun getProfile(uid: Int) {
        viewModelScope.launch {
            _profileState.emit(ProfileState.Loading)
            _profileState.emit(try {
                if (isLogin) {
                    val profileEntity = repository.getProfile(uid)
                    saveProfile(profileEntity)
                    ProfileState.Success(profileEntity)
                } else {
                    ProfileState.Error("UN LOGIN")
                }
            } catch (e: Exception) {
                ProfileState.Error(e.localizedMessage ?: "UNKNOWN")
            })
        }
    }

    /**
     * 保存个人主页信息
     * @param profileEntity 个人主页信息实体
     */
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

    /**
     * 账户消息状态流
     */
    private fun getNotification() {
        viewModelScope.launch {
            _notificationState.emit(NotificationState.Loading)
            _notificationState.emit(
                try {
                    if (isLogin) {
                        NotificationState.Success(Pager(
                            PagingConfig(pageSize = 50, prefetchDistance = 10),
                            pagingSourceFactory = { NotificationSource(repository) }
                        ).flow.cachedIn(viewModelScope))
                    } else {
                        NotificationState.Error("UN LOGIN")
                    }
                } catch (e: Exception) {
                    NotificationState.Error(e.localizedMessage ?: "UNKNOWN")
                }
            )
        }
    }

    /**
     * 信息流评论区状态流
     * @param id 信息流ID
     */
    private fun getReply(id: Int) {
        viewModelScope.launch {
            _replyState.emit(ReplyState.Idle)
            _replyState.emit(
                try {
                    ReplyState.Success(Pager(
                        PagingConfig(pageSize = 50, prefetchDistance = 10),
                        pagingSourceFactory = { ReplySource(repository, id) }
                    ).flow.cachedIn(viewModelScope))
                } catch (e: Exception) {
                    ReplyState.Error(e.localizedMessage ?: "UNKNOWN")
                }
            )
        }
    }
}