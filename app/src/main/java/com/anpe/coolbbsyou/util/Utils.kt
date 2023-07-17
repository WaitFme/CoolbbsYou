package com.anpe.coolbbsyou.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.text.HtmlCompat
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.network.data.model.deviceInfo.DeviceInfo
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.Random


class Utils {
    companion object {
        private val TAG = "Utils"

        private fun String.getBase64(isRaw: Boolean = true): String {
            var result = Base64.getEncoder().encodeToString(this.toByteArray())
            if (isRaw) {
                result = result.replace("=", "")
            }
            return result
        }

        private fun String.getReBase64(isRaw: Boolean = true): String {
            val code = if (isRaw) "$this=" else this
            return Base64.getDecoder().decode(code).decodeToString()
        }

        private fun String.getMD5(): String {
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

        private fun DeviceInfo.createDeviceCode(isRaw: Boolean = true) =
                "$aid; ; ; $mac; $manuFactor; $brand; $model; $buildNumber".getBase64(isRaw).reversed()

        private fun randomMacAddress(): String {
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

        fun String.getDeviceInfo(isRaw: Boolean = true): String = this.reversed().getReBase64(isRaw)

        fun String.getTokenV2(): String {
            val timeStamp = (System.currentTimeMillis() / 1000f).toString()

            val base64TimeStamp = timeStamp.getBase64()
            val md5TimeStamp = timeStamp.getMD5()
            val md5DeviceCode = this.getMD5()

            val token = "${Constants.APP_LABEL}?$md5TimeStamp$$md5DeviceCode&${Constants.APP_ID}"
            val base64Token = token.getBase64()
            val md5Base64Token = base64Token.getMD5()
            val md5Token = token.getMD5()

            val bcryptSalt = "${"$2a$10$$base64TimeStamp/$md5Token".substring(0, 31)}u"
            val bcryptResult = BCrypt.hashpw(md5Base64Token, bcryptSalt)

            return "v2${bcryptResult.getBase64()}"
        }

        fun String.richToString(htmlCompat: Int = HtmlCompat.FROM_HTML_MODE_LEGACY) =
                HtmlCompat.fromHtml(this, htmlCompat).toString()

        fun getDeviceCode(context: Context): String {
            val aid = Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val mac = randomMacAddress()
            val manuFactor = Build.MANUFACTURER
            val brand = Build.BRAND
            val model = Build.MODEL
            val buildNumber = "CoolbbsYou ${Build.VERSION.RELEASE}"

            return DeviceInfo(aid, mac, manuFactor, brand, model, buildNumber).createDeviceCode()
        }
    }
}