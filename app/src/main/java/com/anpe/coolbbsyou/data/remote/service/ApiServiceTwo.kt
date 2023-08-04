package com.anpe.coolbbsyou.data.remote.service

import android.content.Context
import com.anpe.coolbbsyou.data.remote.cookie.CookieManger
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import java.util.concurrent.TimeUnit

interface ApiServiceTwo {
    companion object {
        private const val API_URL = "https://api.coolapk.com"
        private const val API2_URL = "https://api2.coolapk.com"
        private const val ACCOUNT_URL = "https://account.coolapk.com"

        private var service: ApiServiceTwo? = null

        fun getSerVice(context: Context): ApiServiceTwo {
            if (service == null) {
                val client = OkHttpClient.Builder()
                    .cookieJar(CookieManger(context))
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

    @GET("/auth/loginByCoolApk")
    fun getRequestHash(
        @Header("X-Requested-With") requestedWith: String = "com.coolapk.market"
    ): Call<ResponseBody>
}