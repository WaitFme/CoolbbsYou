package com.anpe.coolbbsyou.data.remote.service

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.data.remote.cookie.CookieManager
import com.anpe.coolbbsyou.data.remote.domain.createFeed.CreateFeedModel
import com.anpe.coolbbsyou.data.remote.domain.feedList.FeedListModel
import com.anpe.coolbbsyou.data.remote.domain.follow.FollowModel
import com.anpe.coolbbsyou.data.remote.domain.like.LikeModel
import com.anpe.coolbbsyou.data.remote.domain.loginInfo.LoginInfoModel
import com.anpe.coolbbsyou.data.remote.domain.nofitication.NotificationModel
import com.anpe.coolbbsyou.data.remote.domain.profile.ProfileModel
import com.anpe.coolbbsyou.data.remote.domain.search.SearchModel
import com.anpe.coolbbsyou.data.remote.domain.space.SpaceModel
import com.anpe.coolbbsyou.data.remote.domain.suggest.SuggestSearchModel
import com.anpe.coolbbsyou.data.remote.domain.today.TodayCoolModel
import com.anpe.coolbbsyou.data.remote.domain.topic.TopicModel
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiService {
    companion object {
        private const val BASE_API = "https://api.coolapk.com"

        private var service: ApiService? = null

        fun getSerVice(context: Context): ApiService {
            if (service == null) {
                val deviceCode = TokenDeviceUtils.getDeviceCode(context)

                val client = OkHttpClient.Builder()
                    .cookieJar(CookieManager(context))
                    .callTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor {
                        val request = it.request().newBuilder()
                            .addHeader(Constants.USER_AGENT_KEY, System.getProperty("http.agent") ?: Constants.USER_AGENT_VALUE)
                            .addHeader(Constants.DEVICE_CODE_KEY, deviceCode)
                            .addHeader(Constants.DEVICE_TOKEN_KEY, deviceCode.getTokenV2())
                            .addHeader(Constants.REQUEST_WIDTH_KEY, Constants.REQUEST_WIDTH_VALUE)
                            .addHeader(Constants.APP_ID_KEY, Constants.APP_ID_VALUE)
                            .build()
                        it.proceed(request)
                    }
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(ApiService::class.java)
            }

            return service as ApiService
        }
    }

    @GET("/v6/page/dataList")
    suspend fun getTodayCool(
        @Query("page") page: Int = 1,
        @Query("url") url: String
    ): TodayCoolModel

    @GET("/v6/search/suggestSearchWordsNew")
    suspend fun getSuggestSearch(
        @Query("type") type: String = "app",
        @Query("searchValue") searchValue: String
    ): SuggestSearchModel

    @GET("/v6/search")
    suspend fun getSearch(
        @Query("type") type: String = "all",
        @Query("page") page: Int,
        @Query("searchValue") searchValue: String
    ): SearchModel

    @GET("/v6/notification/list")
    suspend fun getNotification(@Query("page") page: Int): NotificationModel

    @GET("/v6/account/checkLoginInfo")
    suspend fun getLoginInfo(): LoginInfoModel

    @GET("/v6/user/profile")
    suspend fun getProfile(@Query("uid") uid: Int): ProfileModel

    @GET("/v6/feed/like")
    suspend fun getLike(@Query("id") id: Int): LikeModel

    @GET("/v6/feed/unlike")
    suspend fun getUnlike(@Query("id") id: Int): LikeModel

    @GET("/v6/user/follow")
    suspend fun follow(@Query("uid") uid: Int): FollowModel

    @GET("/v6/user/unfollow")
    suspend fun unFollow(@Query("uid") uid: Int): FollowModel

    @Headers("${Constants.APP_CODE_KEY}:${Constants.APP_CODE_VALUE}")
    @GET("/v6/user/space")
    suspend fun space(@Query("uid") uid: Int): SpaceModel

    @POST("/v6/feed/createFeed")
    @FormUrlEncoded
    suspend fun createFeed(
        @Field("message") message: String,
        @Field("type") type: String,
        @Field("status") status: Int,
    ): CreateFeedModel

    @GET("/v6/topic/newTagDetail")
    suspend fun topic(@Query("tag") tag: String): TopicModel

    @GET("/v6/product/detail")
    suspend fun product(@Query("id") id: Int): ProfileModel

    @GET("https://api.coolapk.com/v6/user/feedList")
    suspend fun feedList(
        @Query("uid") uid: Int,
        @Query("page") page: Int,
        @Query("isIncludeTop") isIncludeTop: Int
    ): FeedListModel
}