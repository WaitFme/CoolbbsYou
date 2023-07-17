package com.anpe.coolbbsyou.util

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

class Utils {
    companion object {
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

        fun DeviceInfo.createDeviceCode(isRaw: Boolean = true) =
            "$aid; ; ; $mac; $manufactor; $brand; $model; $buildNumber".getBase64(isRaw).reversed()

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

        fun String.htmlToString(htmlCompat: Int = HtmlCompat.FROM_HTML_MODE_LEGACY) =
            HtmlCompat.fromHtml(this, htmlCompat).toString()

        fun String.richToString(htmlCompat: Int = HtmlCompat.FROM_HTML_MODE_LEGACY) =
            HtmlCompat.fromHtml(this, htmlCompat)
    }
}