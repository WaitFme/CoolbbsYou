package com.anpe.coolbbsyou.util

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.text.HtmlCompat
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random


class Utils {
    companion object {
        private val TAG = this::class.simpleName

        fun Configuration.isTable() = this.screenWidthDp >= 800

        /**
         * 二进制数组转十六进制字符串
         *
         * @param bytes byte array to be converted
         * @return string containing hex values
         */
        fun ByteArray.byteArrayToHexString(): String {
            val sb = StringBuilder(size * 2)
            for (element in this) {
                val v = element.toInt() and 0xff
                if (v < 16) {
                    sb.append('0')
                }
                sb.append(Integer.toHexString(v))
            }
            return sb.toString().uppercase(Locale.US)
        }

        /**
         * 十六进制字符串转二进制数组
         *
         * @param hexString string of hex-encoded values
         * @return decoded byte array
         */
        fun String.hexStringToByteArray(): ByteArray {
            val data = ByteArray(length / 2)
            for (index in indices step 2) {
                data[index / 2] = (
                    (Character.digit(this[index], 16) shl 4) +
                        Character.digit(this[index + 1], 16)
                    ).toByte()
            }
            return data
        }

        fun String.getBase64(isRaw: Boolean = true): String {
            var result = Base64.getEncoder().encodeToString(this.toByteArray())
            if (isRaw) {
                result = result.replace("=", "")
            }
            return result
        }

        fun String.getReBase64(isRaw: Boolean = true): String {
            val code = if (isRaw) "$this=" else this
            return Base64.getDecoder().decode(code).decodeToString()
        }

        fun String.getMD5(): String {
            // 获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")

            // 对字符串加密，返回字节数组
            val digest = instance.digest(this.toByteArray())

            val sb = StringBuffer()

            for (b in digest) {
                // 获取低八位有效值
                val i = b.toInt() and 0xff
                // 将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    // 如果是一位的话，补0
                    hexString = "0$hexString"
                }
                sb.append(hexString)
            }

            return sb.toString().replace("-", "")
        }

        fun randomMacAddress(): String {
            val random = Random()
            val sb = StringBuilder()

            for (i in 0..5) {
                if (sb.isNotEmpty()) {
                    sb.append(":")
                }
                val value = random.nextInt(256)
                val element = Integer.toHexString(value)
                if (element.length < 2) {
                    sb.append(0)
                }
                sb.append(element)
            }

            return sb.toString().uppercase()
        }

        fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
            clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
        }

        fun Int.secondToDateString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
            val date = Date(this.toLong() * 1000)
            return SimpleDateFormat(pattern, Locale.CHINA).format(date)
        }

        fun Int.timeStampInterval(currentTime: Int): String {
            val calendarOld = Calendar.getInstance()
            val calendarNow = Calendar.getInstance()

            val dateOld = Date(this.toLong() * 1000)
            val dateNow = Date(currentTime.toLong() * 1000)

            calendarOld.time = dateOld
            calendarNow.time = dateNow

            val dayOld = calendarOld.get(Calendar.DAY_OF_YEAR)
            val dayNow = calendarNow.get(Calendar.DAY_OF_YEAR)

            val hourOld = calendarNow.get(Calendar.HOUR_OF_DAY)
            val hourNow = calendarNow.get(Calendar.HOUR_OF_DAY)

            val minuteOld = calendarNow.get(Calendar.MINUTE)
            val minuteNow = calendarNow.get(Calendar.MINUTE)

            return if (dayOld == dayNow) {
                if (hourOld == hourNow) {
                    "${minuteNow - minuteOld}分钟前"
                } else {
                    "${hourNow - hourOld}小时前"
                }
            } else {
                "${dayNow - dayOld}天前"
            }
        }

        fun Int.timeStampInterval(currentTime: Long): String {
            val calendarOld = Calendar.getInstance()
            val calendarNow = Calendar.getInstance()

            val dateOld = Date(this.toLong() * 1000)
            val dateNow = Date(currentTime)

            calendarOld.time = dateOld
            calendarNow.time = dateNow

            val dayOld = calendarOld.get(Calendar.DAY_OF_YEAR)
            val dayNow = calendarNow.get(Calendar.DAY_OF_YEAR)

            val hourOld = calendarOld.get(Calendar.HOUR_OF_DAY)
            val hourNow = calendarNow.get(Calendar.HOUR_OF_DAY)

            val minuteOld = calendarOld.get(Calendar.MINUTE)
            val minuteNow = calendarNow.get(Calendar.MINUTE)

            return if (dayOld == dayNow) {
                if (hourOld == hourNow) {
                    "${minuteNow - minuteOld}分钟前"
                } else {
                    "${hourNow - hourOld}小时前"
                }
            } else {
                "${dayNow - dayOld}天前"
            }
        }

        fun String.richToString(htmlCompat: Int = HtmlCompat.FROM_HTML_MODE_LEGACY) =
            HtmlCompat.fromHtml(this, htmlCompat).toString()
    }
}