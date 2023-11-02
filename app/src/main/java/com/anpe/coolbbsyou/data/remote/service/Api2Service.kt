package com.anpe.coolbbsyou.data.remote.service

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.data.remote.cookie.CookieManager
import com.anpe.coolbbsyou.data.remote.domain.details.DetailsModel
import com.anpe.coolbbsyou.data.remote.domain.index.IndexModel
import com.anpe.coolbbsyou.data.remote.domain.reply.ReplyModel
import com.anpe.coolbbsyou.util.TokenDeviceUtils
import com.anpe.coolbbsyou.util.TokenDeviceUtils.Companion.getTokenV2
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface Api2Service {
    companion object {
        private const val BASE_API = "https://api2.coolapk.com"

        private var service: Api2Service? = null

        fun getService(context: Context): Api2Service {
            if (service == null) {
                val deviceCode = TokenDeviceUtils.getDeviceCode(context)

                val client = OkHttpClient.Builder()
                    .cookieJar(CookieManager(context))
                    .callTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor {
                        val request = it.request().newBuilder()
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

                service = retrofit.create(Api2Service::class.java)
            }

            return service as Api2Service
        }
    }

    @GET("/v6/main/indexV8")
    suspend fun getIndex(
        @Query("ids") ids: String = "",
        @Query("installTime") installTime: String,
        @Query("firstItem") firstItem: Int?,
        @Query("lastItem") lastItem: Int?,
        @Query("page") page: Int,
        @Query("firstLaunch") firstLaunch: Int = 0
    ): IndexModel

    @GET("/v6/feed/detail")
    suspend fun getDetails(@Query("id") id: Int): DetailsModel

    @GET("/v6/feed/replyList")
    suspend fun getReply(
        @Query("id") id: Int,
        @Query("discussMode") discussMode: Int = 1,
        @Query("listType") listType: String,
        @Query("page") page: Int
    ): ReplyModel
}