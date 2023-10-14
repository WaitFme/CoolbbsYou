package com.anpe.coolbbsyou.util

import android.util.Log
import com.anpe.coolbbsyou.data.local.entity.cookie.CookieEntity
import com.anpe.coolbbsyou.data.remote.cookie.OkHttpCookies
import com.anpe.coolbbsyou.util.Utils.Companion.byteArrayToHexString
import com.anpe.coolbbsyou.util.Utils.Companion.hexStringToByteArray
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class CookieUtils {
    companion object {
        private val TAG = CookieUtils::class.simpleName

        fun HttpUrl.getDomain(): String {
            return host.split('.').reversed().let {
                if (it.size >= 2) {
                    "${it[1]}.${it[0]}"
                } else {
                    "ERROR"
                }
            }
        }

        fun String.toCookie(url: HttpUrl) = Cookie.parse(url, this)

        fun List<CookieEntity>.toCookieList(url: HttpUrl): List<Cookie> {
            val list = mutableListOf<Cookie>()
            this.forEach {
                it.content.toCookie(url)?.let {
                    list.add(it)
                }
            }
            return list
        }

        /**
         * cookies 序列化成 string
         *
         * @param cookie 要序列化的cookie
         * @return 序列化之后的string
         */
        fun OkHttpCookies.encodeCookie(): String? {
            val os = ByteArrayOutputStream()
            try {
                val outputStream = ObjectOutputStream(os)
                outputStream.writeObject(this)
            } catch (e: IOException) {
                Log.d(TAG, "IOException in encodeCookie", e)
                return null
            }
            return os.toByteArray().byteArrayToHexString()
        }

        /**
         * 将字符串反序列化成cookies
         *
         * @param cookieString cookies string
         * @return cookie object
         */
        fun String.decodeCookie(): Cookie? {
            val bytes = this.hexStringToByteArray()
            val byteArrayInputStream = ByteArrayInputStream(bytes)
            var cookie: Cookie? = null
            try {
                val objectInputStream = ObjectInputStream(byteArrayInputStream)
                cookie = (objectInputStream.readObject() as OkHttpCookies).getCookies()
            } catch (e: IOException) {
                Log.d(TAG, "IOException in decodeCookie", e)
            } catch (e: ClassNotFoundException) {
                Log.d(TAG, "ClassNotFoundException in decodeCookie", e)
            }
            return cookie
        }
    }
}