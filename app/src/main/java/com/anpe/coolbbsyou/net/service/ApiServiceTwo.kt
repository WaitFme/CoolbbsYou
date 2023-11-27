package com.anpe.coolbbsyou.net.service

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.net.cookie.CookieManager
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
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
                    .addInterceptor {
                        val request = it.request().newBuilder()
                            .addHeader(Constants.USER_AGENT_KEY, System.getProperty("http.agent") ?: Constants.USER_AGENT_VALUE)
                            .build()
                        it.proceed(request)
                    }
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

    @GET("/auth/loginByCoolApk")
    fun getRequestHash(): Call<ResponseBody>
}