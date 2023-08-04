package com.anpe.coolbbsyou.data.domain.login
import com.google.gson.annotations.SerializedName


data class LoginEntity(
    @SerializedName("ajaxRequest")
    val ajaxRequest: Boolean,
    @SerializedName("appName")
    val appName: String,
    @SerializedName("baseUrl")
    val baseUrl: String,
    @SerializedName("calledMethodName")
    val calledMethodName: String,
    @SerializedName("charset")
    val charset: String,
    @SerializedName("className")
    val className: String,
    @SerializedName("controllerName")
    val controllerName: String,
    @SerializedName("coolMarketClient")
    val coolMarketClient: Boolean,
    @SerializedName("displayMode")
    val displayMode: String,
    @SerializedName("error")
    val error: Int,
    @SerializedName("forward")
    val forward: String,
    @SerializedName("iosLogin")
    val iosLogin: Boolean,
    @SerializedName("isCommunity")
    val isCommunity: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("messageStatus")
    val messageStatus: Int,
    @SerializedName("requestAjax")
    val requestAjax: Boolean,
    @SerializedName("requestApp")
    val requestApp: String,
    @SerializedName("requestBase")
    val requestBase: String,
    @SerializedName("requestBaseUrl")
    val requestBaseUrl: String,
    @SerializedName("requestHash")
    val requestHash: String,
    @SerializedName("requestTime")
    val requestTime: Int,
    @SerializedName("SESSION")
    val sESSION: SESSION,
    @SerializedName("status")
    val status: Int,
    @SerializedName("themeName")
    val themeName: String
)

data class SESSION(
    @SerializedName("isAdministrator")
    val isAdministrator: Boolean,
    @SerializedName("isLogin")
    val isLogin: Boolean,
    @SerializedName("isMember")
    val isMember: Boolean,
    @SerializedName("isSubAdmin")
    val isSubAdmin: Int,
    @SerializedName("isSuperAdministrator")
    val isSuperAdministrator: Boolean,
    @SerializedName("isTester")
    val isTester: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("uid")
    val uid: Int,
    @SerializedName("username")
    val username: Any
)