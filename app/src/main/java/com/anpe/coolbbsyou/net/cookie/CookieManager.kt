package com.anpe.coolbbsyou.net.cookie

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieManager(context: Context): CookieJar {
    companion object {
        private val TAG = CookieManager::class.simpleName
    }

    private val myCookieStore = MyCookieStore()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return myCookieStore.get(url)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach {
            if (it.value != "deleted") {
                myCookieStore.add(url, it)
            }
        }
    }
}