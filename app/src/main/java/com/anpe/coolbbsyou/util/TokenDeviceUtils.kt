package com.anpe.coolbbsyou.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.util.Utils.Companion.getBase64
import com.anpe.coolbbsyou.util.Utils.Companion.getMD5
import java.util.UUID

class TokenDeviceUtils {
    companion object {
        private fun createDeviceCode(
            id: String,
            mac: String,
            manufacturer: String,
            brand: String,
            model: String,
            display: String,
            str: String,
            isRaw: Boolean = true
        ) = "$id; ; ; $mac; $manufacturer; $brand; $model; $display; $str".getBase64(isRaw)
            .reversed()

        fun getDeviceCode(context: Context): String {
            val sp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)

            var aid = sp.getString(
                "AID",
                Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            )!!

            val uuid = sp.getString("UUID", UUID.randomUUID().toString())!!
            val uuids = uuid.split("-")
            val id = "ca${uuids[0]}_${uuids[1]}${uuids[2]}${uuids[3]}${uuids[4]}"

            val mac = sp.getString("MAC", Utils.randomMacAddress())!!
            val manuFactor = Build.MANUFACTURER
            val brand = Build.BRAND
            val model = Build.MODEL
            val display = Build.DISPLAY
            val str = "null"

            return createDeviceCode(id, mac, manuFactor, brand, model, display, str)
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
    }
}