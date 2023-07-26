package com.anpe.coolbbsyou.util

import android.content.Context

class SharedPreferencesUtils {
    companion object {
        private fun baseSP(context: Context, content: String, mode: Int) =
            context.getSharedPreferences(content, mode)

        private fun edit(context: Context, content: String, mode: Int) =
            baseSP(context, content, mode).edit()

        private fun get(context: Context, content: String, mode: Int) =
            baseSP(context, content, mode)

        fun String.put2SP(
            key: String,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, content, mode).putString(key, this)

        fun Int.put2SP(
            key: String,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, content, mode).putInt(key, this)

        fun Boolean.put2SP(
            key: String,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, content, mode).putBoolean(key, this)

        fun Float.put2SP(
            key: String,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, content, mode).putFloat(key, this)

        fun Long.put2SP(
            key: String,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = edit(context, content, mode).putLong(key, this)

        fun getString(
            key: String,
            defValue: String? = null,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, content, mode).getString(key, defValue)

        fun getInt(
            key: String,
            defValue: Int = -1,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, content, mode).getInt(key, defValue)

        fun getLong(
            key: String,
            defValue: Long = -1L,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, content, mode).getLong(key, defValue)

        fun getFloat(
            key: String,
            defValue: Float = -1f,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, content, mode).getFloat(key, defValue)

        fun getBoolean(
            key: String,
            defValue: Boolean = false,
            content: String = MyApplication.context.packageName,
            context: Context = MyApplication.context,
            mode: Int = Context.MODE_PRIVATE
        ) = baseSP(context, content, mode).getBoolean(key, defValue)
    }
}