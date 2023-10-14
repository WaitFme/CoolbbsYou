package com.anpe.coolbbsyou.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.util.Utils.Companion.getBase64
import com.anpe.coolbbsyou.util.Utils.Companion.getMD5

class TokenDeviceUtils {
    companion object {
        private fun createDeviceCode(aid: String, mac: String, manufacturer: String, brand: String, model: String, buildNumber: String, isRaw: Boolean = true
        ) = "$aid; ; ; $mac; $manufacturer; $brand; $model; $buildNumber".getBase64(isRaw).reversed()

        private fun getDeviceCode(context: Context): String {
            val aid = Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val mac = Utils.randomMacAddress()
            val manuFactor = Build.MANUFACTURER
            val brand = Build.BRAND
            val model = Build.MODEL
            val buildNumber = "CoolbbsYou ${Build.VERSION.RELEASE}"

            return createDeviceCode(aid, mac, manuFactor, brand, model, buildNumber)
        }

        fun String.getTokenV2(): String {
            val timeStamp = (System.currentTimeMillis() / 1000f).toString()

            val base64TimeStamp = timeStamp.getBase64()
            val md5TimeStamp = timeStamp.getMD5()
            val md5DeviceCode = this.getMD5()

            val token =
                "${Constants.APP_LABEL}?$md5TimeStamp$$md5DeviceCode&${Constants.APP_ID_VALUE}"
            val base64Token = token.getBase64()
            val md5Base64Token = base64Token.getMD5()
            val md5Token = token.getMD5()

            val bcryptSalt = "${"$2a$10$$base64TimeStamp/$md5Token".substring(0, 31)}u"
            val bcryptResult = org.mindrot.jbcrypt.BCrypt.hashpw(md5Base64Token, bcryptSalt)

            return "v2${bcryptResult.getBase64()}"
        }

        fun getLastingDeviceCode(context: Context): String {
            val sp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)
            return sp.getString("DEVICE_CODE", null).let {
                it ?: getDeviceCode(context).apply {
                    sp.edit().putString("DEVICE_CODE", this).apply()
                }
            }
        }

        fun getDeviceCodeNew(context: Context): String {
            val sp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)

            val aid = sp.getString("AID", let {
                val aid =
                    Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                sp.edit().putString("AID", aid).apply()
                aid
            })

            val buildNumber = sp.getString("BUILD_NUMBER", let {
                val bn = "CoolbbsYou ${Build.VERSION.RELEASE}"
                sp.edit().putString("BUILD_NUMBER", bn).apply()
                bn
            })

            return createDeviceCode(
                aid!!,
                Utils.randomMacAddress(),
                sp.getString("MANUFACTURER", Build.MANUFACTURER)!!,
                sp.getString("BRAND", Build.BRAND)!!,
                sp.getString("MODEL", Build.MODEL)!!,
                buildNumber!!
            )
        }

        fun getLastingInstallTime(context: Context): String {
            val sp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)
            return sp.getString("INSTALL_TIME", null).let {
                it ?: System.currentTimeMillis().toString().apply {
                    sp.edit().putString("INSTALL_TIME", this).apply()
                }
            }
        }
    }
}