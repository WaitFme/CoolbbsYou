package com.anpe.coolbbsyou.util

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.safety.Safelist

class RichTextUtil {
    companion object {
        private val TAG = this::class.java.simpleName

        fun String.R2T(): String {
            val document = Jsoup.parse(this)

            Log.d(TAG, "R2T: ${document.text()}")
            return this
        }
    }
}