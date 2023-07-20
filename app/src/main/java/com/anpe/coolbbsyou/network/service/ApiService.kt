package com.anpe.coolbbsyou.network.service

import android.content.Context
import com.anpe.bilibiliandyou.network.cookieManager.CookieManger
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.model.index.IndexEntity
import com.anpe.coolbbsyou.network.data.model.suggest.SuggestSearchEntity
import com.anpe.coolbbsyou.network.data.model.today.TodayCoolEntity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object {
        private const val API_URL = "https://api.coolapk.com"
        private const val API2_URL = "https://api2.coolapk.com"

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
        @Header("X-App-Device") device: String,
        @Header("X-App-Token") token: String,
        @Query("ids") ids: String = "",
        @Query("installTime") installTime: String,
        @Query("firstItem") firstItem: Int,
        @Query("page") page: Int,
        @Query("firstLaunch") firstLaunch: Int

    ): IndexEntity

    @GET("/v6/feed/detail")
    suspend fun getDetails(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") deviceCode: String,
        @Header("X-App-Token") token: String,
        @Query("id") id: Int
    ): DetailsEntity

    @GET("$API_URL/v6/page/dataList")
    suspend fun getTodayCool(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") deviceCode: String,
        @Header("X-App-Token") token: String,
        @Query("page") page: Int = 1,
        @Query("url") url: String
    ): TodayCoolEntity

    @GET("$API_URL/v6/search/suggestSearchWordsNew")
    suspend fun getSuggestSearch(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") deviceCode: String,
        @Header("X-App-Token") token: String,
        @Query("type") type: String = "app",
        @Query("searchValue") searchValue: String
    ): SuggestSearchEntity
}