package com.anpe.coolbbsyou.util

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToastBase

class ToastUtils {
    companion object {
        private fun Context.showToastBase(
            content: String
        ) = Toast.makeText(this, content, Toast.LENGTH_SHORT).show()

        fun Application.showToast(
            content: String
        ) = Toast.makeText(this, content, Toast.LENGTH_SHORT).show()

        fun Context.showToast(
            content: String
        ) = Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }
}