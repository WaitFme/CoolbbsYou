package com.anpe.coolbbsyou.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.network.data.model.deviceInfo.DeviceInfo
import com.anpe.coolbbsyou.util.Utils.Companion.getBase64
import com.anpe.coolbbsyou.util.Utils.Companion.getMD5
import com.anpe.coolbbsyou.util.Utils.Companion.getReBase64

class TokenDeviceUtils {
    companion object {
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
            val bcryptResult = org.mindrot.jbcrypt.BCrypt.hashpw(md5Base64Token, bcryptSalt)

            return "v2${bcryptResult.getBase64()}"
        }

        private fun DeviceInfo.createDeviceCode(isRaw: Boolean = true) =
            "$aid; ; ; $mac; $manuFactor; $brand; $model; $buildNumber".getBase64(isRaw).reversed()


        fun getDeviceCode(context: Context): String {
            val aid = Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val mac = Utils.randomMacAddress()
            val manuFactor = Build.MANUFACTURER
            val brand = Build.BRAND
            val model = Build.MODEL
            val buildNumber = "CoolbbsYou ${Build.VERSION.RELEASE}"

            return DeviceInfo(aid, mac, manuFactor, brand, model, buildNumber).createDeviceCode()
        }

        fun getLastingDeviceCode(context: Context): String {
            val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            return sp.getString("DEVICE_CODE", null).let {
                it ?: getDeviceCode(context).apply {
                    sp.edit().putString("DEVICE_CODE", this).apply()
                }
            }
        }

        fun getLastingInstallTime(context: Context): String {
            val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            return sp.getString("INSTALL_TIME", null).let {
                it ?: System.currentTimeMillis().toString().apply {
                    sp.edit().putString("INSTALL_TIME", this).apply()
                }
            }
        }
    }
}