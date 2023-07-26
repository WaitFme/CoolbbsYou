package com.anpe.coolbbsyou.util

import android.content.Context
import android.widget.Toast

class ToastUtils {
    companion object {
        private fun String.showToastBase(
            context: Context,
            duration: Int
        ) = Toast.makeText(context, this, duration).show()

        fun String.showToast(
            context: Context = MyApplication.context,
            duration: Int = Toast.LENGTH_SHORT
        ) = this.showToastBase(context, duration)

        fun Int.showToast(
            context: Context = MyApplication.context,
            duration: Int = Toast.LENGTH_SHORT
        ) = this.toString().showToastBase(context, duration)

        fun Boolean.showToast(
            context: Context = MyApplication.context,
            duration: Int = Toast.LENGTH_SHORT
        ) = this.toString().showToastBase(context, duration)

        fun Float.showToast(
            context: Context = MyApplication.context,
            duration: Int = Toast.LENGTH_SHORT
        ) = this.toString().showToastBase(context, duration)

        fun Double.showToast(
            context: Context = MyApplication.context,
            duration: Int = Toast.LENGTH_SHORT
        ) = this.toString().showToastBase(context, duration)

        fun showToastString(
            text: String,
            context: Context = MyApplication.context,
            duration: Int = Toast.LENGTH_SHORT
        ) = text.showToastBase(context, duration)
    }
}