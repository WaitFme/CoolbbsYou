package com.anpe.coolbbsyou.ui.main

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.DetailsState
import com.anpe.coolbbsyou.intent.state.IndexImageState
import com.anpe.coolbbsyou.intent.state.IndexState
import com.anpe.coolbbsyou.intent.state.LikeState
import com.anpe.coolbbsyou.intent.state.LoginInfoState
import com.anpe.coolbbsyou.intent.state.LoginState
import com.anpe.coolbbsyou.intent.state.NotificationState
import com.anpe.coolbbsyou.intent.state.ProfileState
import com.anpe.coolbbsyou.intent.state.ReplyState
import com.anpe.coolbbsyou.intent.state.SearchState
import com.anpe.coolbbsyou.intent.state.SuggestState
import com.anpe.coolbbsyou.intent.state.TodayState
import com.anpe.coolbbsyou.intent.state.feedList.FeedListState
import com.anpe.coolbbsyou.intent.state.global.GlobalState
import com.anpe.coolbbsyou.intent.state.global.ImageArray
import com.anpe.coolbbsyou.intent.state.space.SpaceState
import com.anpe.coolbbsyou.intent.state.topic.TopicState
import com.anpe.coolbbsyou.net.cookie.MyCookieStore
import com.anpe.coolbbsyou.net.model.createFeed.CreateFeedModel
import com.anpe.coolbbsyou.net.model.follow.FollowModel
import com.anpe.coolbbsyou.net.model.index.Data
import com.anpe.coolbbsyou.net.model.profile.ProfileModel
import com.anpe.coolbbsyou.net.repository.RemoteRepository
import com.anpe.coolbbsyou.page.IndexSource
import com.anpe.coolbbsyou.page.NotificationSource
import com.anpe.coolbbsyou.page.ReplySource
import com.anpe.coolbbsyou.page.SearchSource
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.util.LoginUtils.Companion.getRequestHash
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2
import com.anpe.coolbbsyou.util.Utils
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private val TAG = MainViewModel::class.simpleName

        fun Exception.message() = this.localizedMessage ?: "UNKNOWN"
    }

    private val repository = RemoteRepository(application)

    private val configSp =
        application.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)

    private val userInfoSp =
        application.getSharedPreferences(Constants.USER_INFO_PREFS, Context.MODE_PRIVATE)

    private val cookieSp =
        application.getSharedPreferences(Constants.COOKIE_PREFS, Context.MODE_PRIVATE)

    val channel = Channel<MainEvent>(Channel.UNLIMITED)

    private val _indexState = MutableStateFlow<IndexState>(IndexState.Idle)
    val indexState: StateFlow<IndexState> = _indexState

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val detailsState: StateFlow<DetailsState> = _detailsState

    private val _indexImageState = MutableStateFlow<IndexImageState>(IndexImageState.ImageRow)
    val indexImageState: StateFlow<IndexImageState> = _indexImageState

    private val _suggestState = MutableStateFlow<SuggestState>(SuggestState.Idle)
    val suggestState: StateFlow<SuggestState> = _suggestState

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState

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

    private val _replyDetailState = MutableStateFlow<ReplyState>(ReplyState.Idle)
    val replyDetailState: StateFlow<ReplyState> = _replyDetailState

    private val _createFeedState = MutableStateFlow(CreateFeedModel())
    val createFeedState = _createFeedState.asStateFlow()

    private val _spaceState = MutableStateFlow<SpaceState>(SpaceState.Idle)
    val spaceState = _spaceState.asStateFlow()

    private val _topicState = MutableStateFlow<TopicState>(TopicState.Idle)
    val topicState = _topicState.asStateFlow()

    private val _feedListState = MutableStateFlow<FeedListState>(FeedListState.Idle)
    val feedListState = _feedListState.asStateFlow()

    private val _followState = MutableStateFlow(FollowModel())
    val followState = _followState.asStateFlow()

    private val _likeState = MutableStateFlow(LikeState())
    val likeState = _likeState.asStateFlow()

    private val _globalState = MutableStateFlow(GlobalState())
    val globalState = _globalState.asStateFlow()

    var indexPagingDataFlow: Flow<PagingData<Data>>? = null

    var replyPagingDataFlow: Flow<PagingData<com.anpe.coolbbsyou.net.model.reply.Data>>? = null

    var replyDetailPagingDataFlow: Flow<PagingData<com.anpe.coolbbsyou.net.model.reply.Data>>? = null

    init {
        val deviceCode = TokenDeviceUtils.getDeviceCode(application)
        println(deviceCode)
        println(deviceCode.getTokenV2())

        channelHandler(channel)
    }

    private fun channelHandler(channel: Channel<MainEvent>) {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when (it) {
                    is MainEvent.GetIndex -> getIndexState()
                    is MainEvent.GetDetails -> getDetailsState(it.id)
                    is MainEvent.OpenNineGrid -> isNineGrid(it.isNineGrid)
                    is MainEvent.GetSuggestSearch -> getSuggestSearch(it.keyword)
                    is MainEvent.GetSearch -> getSearch(it.keyword)
                    is MainEvent.GetTodayCool -> getTodayCool(it.url, it.page)
                    is MainEvent.LoginAccount -> loginAccount(it.account, it.passwd, it.requestHash)
                    is MainEvent.LogoutAccount -> logoutAccount()
                    is MainEvent.GetLoginInfo -> getLoginInfo()
                    is MainEvent.GetProfile -> getProfile(it.uid)
                    is MainEvent.GetNotification -> getNotification()
                    is MainEvent.GetReply -> getReply(it.id)
                    is MainEvent.GetReplyDetail -> getReplyDetail(it.id)
                    is MainEvent.Like -> getLike(it.id)
                    is MainEvent.Unlike -> getUnlike(it.id)
                    is MainEvent.Follow -> getFollow(it.uid)
                    is MainEvent.Unfollow -> getUnFollow(it.uid)
                    is MainEvent.CreateFeed -> createFeed(it.message)
                    is MainEvent.GetSpace -> getUserSpace(it.uid)
                    is MainEvent.GetTopic -> getTopic(it.topic)
                    is MainEvent.GetFeedList -> getFeedList(it.uid, it.page)
                    else -> {}
                }
            }
        }
    }

    private fun getFeedList(uid: Int, page: Int) {
        viewModelScope.launch {
            _feedListState.emit(FeedListState.Loading)
            try {
                val feedListModel = repository.getFeedList(uid, page)
                _feedListState.emit(FeedListState.Success(feedListModel))
            } catch (e: Exception) {
                _feedListState.emit(FeedListState.Error(e.message()))
            }
        }
    }

    private fun getTopic(topic: String) {
        viewModelScope.launch {
            try {
                _topicState.emit(TopicState.Loading)
                val topicModel = repository.getTopic(topic)
                _topicState.emit(TopicState.Success(topicModel))
            } catch (e: Exception) {
                println(e)
                _topicState.emit(TopicState.Error(e.message()))
            }
        }
    }

    private fun getUserSpace(uid: Int) {
        viewModelScope.launch {
            _spaceState.emit(SpaceState.Loading)
            try {
                val spaceModel = repository.getSpace(uid)
                _spaceState.emit(SpaceState.Success(spaceModel))
            } catch (e: Exception) {
                _spaceState.emit(SpaceState.Error(e.message()))
            }
        }
    }

    private fun createFeed(message: String) {
        viewModelScope.launch {
            try {
                val feedModel = repository.createFeed(message)
                _createFeedState.emit(feedModel)
            } catch (e: Exception) {
                Log.e(TAG, "createFeed: ${e.localizedMessage}")
            }
        }
    }

    /**
     * 更新获取首页推荐状态流
     */
    private fun getIndexState() {
        viewModelScope.launch {
            val pagingDataFlow = Pager(
                config = PagingConfig(pageSize = 18, prefetchDistance = 6), //10 1
                pagingSourceFactory = { IndexSource(repository) }
            ).flow.cachedIn(viewModelScope)

            indexPagingDataFlow = pagingDataFlow
        }
    }

    /**
     * 首页推荐信息流图片排列方式
     * @param isNineGrid true: 九宫格排列; false: 横向列表排列
     */
    private fun isNineGrid(isNineGrid: Boolean) {
        viewModelScope.launch {
            _globalState.value.isNineGrid = isNineGrid
            _indexImageState.emit(
                if (isNineGrid) {
                    IndexImageState.NineGrid
                } else {
                    IndexImageState.ImageRow
                }
            )
            configSp.edit().putBoolean("IS_NINE_GRID", isNineGrid).apply()
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
                    DetailsState.Error(e.message())
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
            _suggestState.emit(
                try {
                    SuggestState.Success(repository.getSuggestSearch(keyword = keyword))
                } catch (e: Exception) {
                    SuggestState.Error("${e.message()}; keyword: $keyword")
                }
            )
        }
    }

    /**
     * 搜索状态流
     * @param keyword 搜索关键字
     */
    private fun getSearch(keyword: String) {
        viewModelScope.launch {
            _searchState.emit(SearchState.Loading)
            _searchState.emit(
                try {
                    SearchState.Success(async {
                        Pager(
                            config = PagingConfig(
                                pageSize = 50,
                                prefetchDistance = 10
                            ),
                            pagingSourceFactory = {
                                SearchSource(repository, keyword)
                            }
                        )
                    }.await())
                } catch (e: Exception) {
                    SearchState.Error(e.message())
                }
            )
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
            _todayState.emit(
                try {
                    TodayState.Success(repository.getTodayCool(page, url))
                } catch (e: Exception) {
                    TodayState.Error(e.message())
                }
            )
        }
    }

    /**
     * 登陆账户状态流
     * @param account 账号
     * @param passwd 密码
     * @param requestHash 盐值
     */
    private fun loginAccount(account: String, passwd: String, requestHash: String) {
        viewModelScope.launch {
            _loginState.emit(LoginState.LoggingIn)
            _loginState.emit(
                try {
                    val postAccount = repository.postAccount(requestHash, account, passwd)
                    Log.d(TAG, "loginAccount: $postAccount")
                    LoginState.Success(postAccount)
                } catch (e: Exception) {
                    LoginState.Error(e.message())
                }
            )
        }
    }

    /**
     * 退出账户
     */
    private fun logoutAccount() {
        configSp.edit().putBoolean("LOGIN_STATUS", false).apply()
        _globalState.value.isLogin = false
        userInfoSp.edit().clear().apply()
        MyCookieStore().removeAll()
    }

    /**
     * 登陆信息状态流
     */
    private fun getLoginInfo() {
        viewModelScope.launch {
            _loginInfoState.emit(LoginInfoState.Loading)
            try {
                val loginInfoModel = repository.getLoginInfo()

                val loginStatus: Boolean

                if (loginInfoModel.error == -1) {
                    loginStatus = false
                    _loginInfoState.emit(LoginInfoState.Error("${loginInfoModel.message}; 请登陆"))
                } else {
                    loginStatus = true
                    userInfoSp.edit().putInt("UID", loginInfoModel.data.uid.toInt()).apply()
                    _loginInfoState.emit(LoginInfoState.Success(loginInfoModel))
                }

                _globalState.value.isLogin = loginStatus
                configSp.edit().putBoolean("LOGIN_STATUS", loginStatus).apply()
            } catch (e: Exception) {
                _loginInfoState.emit(LoginInfoState.Error(e.message()))
            }
        }
    }

    /**
     * 个人主页信息状态流
     * @param uid 账户ID
     */
    private fun getProfile(uid: Int) {
        viewModelScope.launch {
            _profileState.emit(ProfileState.Loading)
            try {
                if (globalState.value.isLogin) {
                    val profileModel = repository.getProfile(uid)
                    saveProfile(profileModel)
                    _profileState.emit(ProfileState.Success(profileModel))
                } else {
                    _profileState.emit(ProfileState.UnLogin("UN LOGIN"))
                }
            } catch (e: Exception) {
                _profileState.emit(ProfileState.Error(e.message()))
            }
        }
    }

    /**
     * 保存个人主页信息
     * @param profileModel 个人主页信息实体
     */
    private fun saveProfile(profileModel: ProfileModel) {
        profileModel.data.apply {
            userInfoSp.edit().putInt("UID", uid).apply()
            userInfoSp.edit().putString("USER_NAME", username).apply()
            userInfoSp.edit().putInt("LEVEL", level).apply()
            userInfoSp.edit().putInt("EXPERIENCE", experience).apply()
            userInfoSp.edit().putInt("NEXT_LEVEL_EXPERIENCE", nextLevelExperience).apply()
            userInfoSp.edit().putInt("FEED", feed).apply()
            userInfoSp.edit().putInt("FOLLOW", follow).apply()
            userInfoSp.edit().putInt("FANS", fans).apply()
            userInfoSp.edit().putString("NEXT_LEVEL_PERCENTAGE", nextLevelPercentage).apply()
            userInfoSp.edit().putString("USER_AVATAR", userAvatar).apply()
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
                    if (globalState.value.isLogin) {
                        NotificationState.Success(Pager(
                            PagingConfig(pageSize = 50, prefetchDistance = 10),
                            pagingSourceFactory = { NotificationSource(repository) }
                        ).flow.cachedIn(viewModelScope))
                    } else {
                        NotificationState.Error("UN LOGIN")
                    }
                } catch (e: Exception) {
                    NotificationState.Error(e.message())
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
            val pagingDataFlow = Pager(
                PagingConfig(pageSize = 20, prefetchDistance = 5),
                pagingSourceFactory = {
                    ReplySource(
                        repository = repository,
                        id = id,
                        listType = "lastupdate_desc",
                        discussMode = 1,
                        feedType = "feed"
                    )
                }
            ).flow.cachedIn(viewModelScope)

            replyPagingDataFlow = pagingDataFlow
        }
    }

    private fun getReplyDetail(id: Int) {
        viewModelScope.launch {
            val pagingDataFlow = Pager(
                PagingConfig(pageSize = 20, prefetchDistance = 5),
                pagingSourceFactory = {
                    ReplySource(
                        repository = repository,
                        id = id,
                        listType = "",
                        discussMode = 0,
                        feedType = "feed_reply"
                    )
                }
            ).flow.cachedIn(viewModelScope)

            replyDetailPagingDataFlow = pagingDataFlow
        }
    }

    private fun getFollow(uid: Int) {
        viewModelScope.launch {
            if (globalState.value.isLogin) {
                try {
                    val followModel = repository.getFollow(uid)
                    _followState.emit(followModel)
                } catch (e: Exception) {
                    Log.e(TAG, "getFollow: ${e.message()}")
                }
            } else {
                Log.e(TAG, "getFollow: UN_LOGIN")
            }
        }
    }

    private fun getUnFollow(uid: Int) {
        viewModelScope.launch {
            if (globalState.value.isLogin) {
                try {
                    val followModel = repository.getUnFollow(uid)
                    _followState.emit(followModel)
                } catch (e: Exception) {
                    Log.e(TAG, "getFollow: ${e.message()}")
                }
            } else {
                Log.e(TAG, "getUnFollow: UN_LOGIN")
            }
        }
    }

    private fun getLike(id: Int) {
        viewModelScope.launch {
            if (globalState.value.isLogin) {
                try {
                    val likeModel = repository.getLike(id = id)
                    val likeState = LikeState(isLike = true, likeModel = likeModel)
                    _likeState.emit(likeState)
                } catch (e: Exception) {
                    Log.e(TAG, "getLike: ${e.message()}")
                }
            } else {
                Log.e(TAG, "getLike: UN_LOGIN")
            }
        }
    }

    private fun getUnlike(id: Int) {
        viewModelScope.launch {
            if (globalState.value.isLogin) {
                try {
                    val likeModel = repository.getUnlike(id = id)
                    val likeState = LikeState(isLike = false, likeModel = likeModel)
                    _likeState.emit(likeState)
                } catch (e: Exception) {
                    Log.e(TAG, "getUnlike: ${e.message()}")
                }
            } else {
                Log.e(TAG, "getUnlike: UN_LOGIN")
            }
        }
    }

    fun createSystemInfo(context: Context) {
        if (configSp.getString("AID", null) == null) {
            configSp.edit().putString(
                "AID",
                Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ).apply()
        }
        if (configSp.getString("UUID", null) == null) {
            configSp.edit().putString("UUID", UUID.randomUUID().toString()).apply()
        }

        if (configSp.getString("MAC", null) == null) {
            configSp.edit().putString("MAC", Utils.randomMacAddress()).apply()
        }

        if (configSp.getString("INSTALL_TIME", null) == null) {
            configSp.edit().putString("INSTALL_TIME", System.currentTimeMillis().toString()).apply()
        }

    }

    fun showImage(
        initialCount: Int,
        picArr: List<String>,
        navControllerScreen: NavHostController
    ) {
        viewModelScope.launch {
            _globalState.value.isShow = true
            _globalState.value.imageArray = ImageArray(
                initialCount = initialCount,
                picArray = picArr
            )
            navControllerScreen.navigate(ScreenManager.ImageScreen.route)
        }
    }

    fun showImageTest(
        initialCount: Int,
        picArr: List<String>,
    ) {
        viewModelScope.launch {
            _globalState.value.isShow = true
            _globalState.value.imageArray = ImageArray(
                initialCount = initialCount,
                picArray = picArr
            )
        }
    }

    fun closeImage() {
        viewModelScope.launch {
            _globalState.value.isShow = false
        }
    }

    fun getRequestHash() {
        viewModelScope.launch {
            try {
                _globalState.value.requestHash =
                    repository.getRequestHash().body()!!.string().getRequestHash()!!
            } catch (e: Exception) {
                Log.e(TAG, "getRequestHashTest: ${e.localizedMessage}")
            }
        }
    }
}