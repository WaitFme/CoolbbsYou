package com.anpe.coolbbsyou.util

import android.content.Context
import com.anpe.coolbbsyou.constant.Constants

class SharedPreferencesUtils {
    companion object {
        private fun baseSP(context: Context, name: String, mode: Int) =
            context.getSharedPreferences(name, mode)

        private fun edit(context: Context, content: String, mode: Int) =
            baseSP(context, content, mode).edit()

        private fun get(context: Context, content: String, mode: Int) =
            baseSP(context, content, mode)

        fun String.put2SP(
            key: String,
            name: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, name, mode).putString(key, this)

        fun Int.put2SP(
            key: String,
            name: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, name, mode).putInt(key, this)

        fun Boolean.put2SP(
            key: String,
            name: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, name, mode).putBoolean(key, this)

        fun Float.put2SP(
            key: String,
            name: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, name, mode).putFloat(key, this)

        fun Long.put2SP(
            key: String,
            name: String = Constants.USER_INFO_PREFS,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, name, mode).putLong(key, this)

        fun getString(
            key: String,
            defValue: String? = null,
            name: String = Constants.USER_INFO_PREFS,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, name, mode).getString(key, defValue)

        fun getInt(
            key: String,
            defValue: Int = -1,
            name: String = Constants.USER_INFO_PREFS,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, name, mode).getInt(key, defValue)

        fun getLong(
            key: String,
            defValue: Long = -1L,
            name: String = Constants.USER_INFO_PREFS,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, name, mode).getLong(key, defValue)

        fun getFloat(
            key: String,
            defValue: Float = -1f,
            name: String = Constants.USER_INFO_PREFS,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, name, mode).getFloat(key, defValue)

        fun getBoolean(
            key: String,
            defValue: Boolean = false,
            name: String = Constants.USER_INFO_PREFS,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, name, mode).getBoolean(key, defValue)
    }
}