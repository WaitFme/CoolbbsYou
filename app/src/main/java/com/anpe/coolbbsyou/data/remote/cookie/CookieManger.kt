package com.anpe.coolbbsyou.data.remote.cookie

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieManger(context: Context): CookieJar {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private val cookieStore = PersistentCookieStore(context)

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url]
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies.isNotEmpty()) {
            for (item in cookies) {
                cookieStore.add(url, item)
            }
        }
    }
}