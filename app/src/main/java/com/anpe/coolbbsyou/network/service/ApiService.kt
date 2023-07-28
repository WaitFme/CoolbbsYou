package com.anpe.coolbbsyou.network.service

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.network.data.cookie.CookieManger
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.model.index.IndexEntity
import com.anpe.coolbbsyou.network.data.model.login.LoginEntity
import com.anpe.coolbbsyou.network.data.model.loginState.LoginStateEntity
import com.anpe.coolbbsyou.network.data.model.nofitication.NotificationEntity
import com.anpe.coolbbsyou.network.data.model.profile.ProfileEntity
import com.anpe.coolbbsyou.network.data.model.reply.ReplyEntity
import com.anpe.coolbbsyou.network.data.model.suggest.SuggestSearchEntity
import com.anpe.coolbbsyou.network.data.model.today.TodayCoolEntity
import com.anpe.coolbbsyou.util.LoginUtils
import com.anpe.coolbbsyou.util.MyApplication
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object {
        private const val API_URL = "https://api.coolapk.com"
        private const val API2_URL = "https://api2.coolapk.com"
        private const val ACCOUNT_URL = "https://account.coolapk.com"

        private val deviceCode = TokenDeviceUtils.getLastingDeviceCode(MyApplication.context)
        private val deviceToken = deviceCode.getTokenV2()

        private var service: ApiService? = null

        fun getSerVice(context: Context): ApiService {
            if (service == null) {
                val client = OkHttpClient.Builder()
                    .cookieJar(CookieManger(context))
                    .callTimeout(5, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(API2_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(ApiService::class.java)
            }

            return service as ApiService
        }
    }

    @GET("/v6/main/indexV8")
    suspend fun getIndex(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("ids") ids: String = "",
        @Query("installTime") installTime: String,
        @Query("lastItem") lastItem: Int?,
        @Query("page") page: Int,
        @Query("firstLaunch") firstLaunch: Int

    ): IndexEntity

    @GET("/v6/feed/detail")
    suspend fun getDetails(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("id") id: Int
    ): DetailsEntity

    @GET("$API_URL/v6/page/dataList")
    suspend fun getTodayCool(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("page") page: Int = 1,
        @Query("url") url: String
    ): TodayCoolEntity

    @GET("$API_URL/v6/search/suggestSearchWordsNew")
    suspend fun getSuggestSearch(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("type") type: String = "app",
        @Query("searchValue") searchValue: String
    ): SuggestSearchEntity

    @FormUrlEncoded
    @POST("$ACCOUNT_URL/auth/loginByCoolApk")
    suspend fun postAccount(
        @Header("X-Requested-With") requestedWith: String = "XMLHttpRequest",
        @Field("submit") submit: Int = 1,
        @Field("randomNumber") randomNumber: String = LoginUtils.createRandomNumber(),
        @Field("requestHash") requestHash: String,
        @Field("login") login: String,
        @Field("password") password: String,
        @Field("captcha") captcha: String = "",
        @Field("code") code: String = "",
    ): LoginEntity

    @GET("$API_URL/v6/notification/list")
    suspend fun getNotification(
        @Header("X-Requested-With") requestedWith: String = "XMLHttpRequest",
        @Header("X-App-Id") appId: String = "com.coolapk.market",
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("page") page: Int
    ): NotificationEntity

    @GET("$API_URL/v6/account/checkLoginInfo")
    suspend fun getLoginState(
        @Header("X-Requested-With") requestedWith: String = "XMLHttpRequest",
        @Header("X-App-Id") appId: String = "com.coolapk.market",
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
    ): LoginStateEntity

    @GET("$API_URL/v6/user/profile")
    suspend fun getProfile(
        @Header("X-Requested-With") requestedWith: String = "XMLHttpRequest",
        @Header("X-App-Id") appId: String = "com.coolapk.market",
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("uid") uid: Int
    ): ProfileEntity

    @GET("/v6/feed/replyList")
    suspend fun getReply(
        @Header("X-Requested-With") requestedWith: String = "XMLHttpRequest",
        @Header("X-App-Id") appId: String = "com.coolapk.market",
        @Header("X-App-Device") device: String = deviceCode,
        @Header("X-App-Token") token: String = deviceToken,
        @Query("id") id: Int,
        @Query("listType") listType: String,
        @Query("page") page: Int
    ): ReplyEntity
}