package com.anpe.coolbbsyou.data.remote.service

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.data.remote.cookie.CookieManager
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

interface ApiServiceTwo {
    companion object {
        private const val ACCOUNT_URL = "https://account.coolapk.com"

        private var service: ApiServiceTwo? = null

        fun getSerVice(context: Context): ApiServiceTwo {
            if (service == null) {
                val client = OkHttpClient.Builder()
                    .cookieJar(CookieManager(context))
                    .callTimeout(5, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(ACCOUNT_URL)
                    .client(client)
                    .build()

                return retrofit.create(ApiServiceTwo::class.java)
            }

            return service as ApiServiceTwo
        }
    }

    @Headers("User-Agent:${Constants.USER_AGENT_BAK}")
    @GET("/auth/loginByCoolApk")
    fun getRequestHash(): Call<ResponseBody>
}