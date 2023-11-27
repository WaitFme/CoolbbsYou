package com.anpe.coolbbsyou.net.cookie

import okhttp3.Cookie
import okhttp3.HttpUrl

interface CookieStore {
    fun add(url: HttpUrl, cookie: Cookie)

    fun get(url: HttpUrl): List<Cookie>

    fun getCookies(): List<Cookie>

    fun getUrls(): List<HttpUrl>

    fun remove(url: HttpUrl, cookie: Cookie): Boolean

    fun removeAll(): Boolean
}