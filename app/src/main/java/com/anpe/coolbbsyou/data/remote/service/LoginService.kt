package com.anpe.coolbbsyou.data.remote.service

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.data.remote.cookie.CookieManager
import com.anpe.coolbbsyou.data.remote.domain.login.LoginModel
import com.anpe.coolbbsyou.util.LoginUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface LoginService {
    companion object {
        private const val BASE_API = "https://account.coolapk.com"

        private var service: LoginService? = null

        fun getService(context: Context): LoginService {
            if (service == null) {
                val client = OkHttpClient.Builder()
                    .cookieJar(CookieManager(context))
                    .callTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor {
                        val request = it.request().newBuilder()
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

                service =  retrofit.create(LoginService::class.java)
            }

            return service as LoginService
        }
    }

    @FormUrlEncoded
    @POST("/auth/loginByCoolApk")
    suspend fun postAccount(
        @Field("submit") submit: Int = 1,
        @Field("randomNumber") randomNumber: String = LoginUtils.createRandomNumber(),
        @Field("requestHash") requestHash: String,
        @Field("login") login: String,
        @Field("password") password: String,
        @Field("captcha") captcha: String = "",
        @Field("code") code: String = "",
    ): LoginModel
}