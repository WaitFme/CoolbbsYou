package com.anpe.coolbbsyou.network.service

import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.network.data.model.details.DetailsEntity
import com.anpe.coolbbsyou.network.data.model.index.IndexEntity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    companion object {
        private const val BASE_URL = "https://api2.coolapk.com"

        private var service: ApiService? = null

        fun getSerVice(): ApiService {
            if (service == null) {
                val client = OkHttpClient.Builder()
                    .callTimeout(5, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
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
//        @Header("User-Agent") userAgent: String = Constants.USER_AGENT,
//        @Header("Host") host: String = "api2.coolapk.com",
        @Query("page") page: Int,
        @Query("firstLaunch") firstLaunch: Int,
        @Query("installTime") installTime: String,
        @Query("firstItem") firstItem: Int,
        @Query("ids") ids: String
    ): IndexEntity

    @GET("/v6/feed/detail")
    suspend fun getDetails(
        @Header("X-Requested-With") requestedWith: String = Constants.REQUEST_WIDTH,
        @Header("X-App-Id") appId: String = Constants.APP_ID,
        @Header("X-App-Device") deviceCode: String,
        @Header("X-App-Token") token: String,
        @Query("id") id: Int
    ): DetailsEntity
}