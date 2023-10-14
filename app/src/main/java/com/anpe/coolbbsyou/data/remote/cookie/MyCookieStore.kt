package com.anpe.coolbbsyou.data.remote.cookie

import com.anpe.coolbbsyou.data.local.entity.cookie.CookieEntity
import com.anpe.coolbbsyou.data.local.repository.LocalRepository
import com.anpe.coolbbsyou.util.CookieUtils.Companion.decodeCookie
import com.anpe.coolbbsyou.util.CookieUtils.Companion.encodeCookie
import com.anpe.coolbbsyou.util.CookieUtils.Companion.getDomain
import okhttp3.Cookie
import okhttp3.HttpUrl

class MyCookieStore: CookieStore {
    private val repository: LocalRepository = LocalRepository()

    override fun add(url: HttpUrl, cookie: Cookie) {
        OkHttpCookies(cookie).encodeCookie()?.let {
            val entity = CookieEntity(
                name = cookie.name,
                domain = cookie.domain,
                content = it
            )
            repository.upsertCookie(entity)
        }
    }

    override fun get(url: HttpUrl): List<Cookie> {
        val cookies = repository.getCookie(url.getDomain()).run {
            val list = mutableListOf<Cookie>()
            forEach {
                val cookie = it.content.decodeCookie()
                if (cookie != null) {
                    list.add(cookie)
                }
            }
            list
        }
        return cookies
    }

    override fun getCookies(): List<Cookie> {
        val cookies = repository.getCookieAll().run {
            val list = mutableListOf<Cookie>()
            forEach {
                val cookie = it.content.decodeCookie()
                if (cookie != null) {
                    list.add(cookie)
                }
            }
            list
        }
        return cookies
    }

    override fun getUrls(): List<HttpUrl> {
        return listOf()
    }

    override fun remove(url: HttpUrl, cookie: Cookie): Boolean {
        repository.deleteCookieAll()
        return true
    }

    override fun removeAll(): Boolean {
        repository.deleteCookieAll()
        return true
    }
}