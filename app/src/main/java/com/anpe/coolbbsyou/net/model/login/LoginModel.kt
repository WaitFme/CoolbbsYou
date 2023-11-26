package com.anpe.coolbbsyou.net.model.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginModel(
    @Json(name = "ajaxRequest")
    val ajaxRequest: Boolean = false,
    @Json(name = "appName")
    val appName: String = "",
    @Json(name = "baseUrl")
    val baseUrl: String = "",
    @Json(name = "calledMethodName")
    val calledMethodName: String = "",
    @Json(name = "charset")
    val charset: String = "",
    @Json(name = "className")
    val className: String = "",
    @Json(name = "controllerName")
    val controllerName: String = "",
    @Json(name = "coolMarketClient")
    val coolMarketClient: Boolean = false,
    @Json(name = "displayMode")
    val displayMode: String = "",
    @Json(name = "error")
    val error: Int = 0,
    @Json(name = "forward")
    val forward: String = "",
    @Json(name = "iosLogin")
    val iosLogin: Boolean = false,
    @Json(name = "isCommunity")
    val isCommunity: Boolean = false,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "messageStatus")
    val messageStatus: Int = 0,
    @Json(name = "requestAjax")
    val requestAjax: Boolean = false,
    @Json(name = "requestApp")
    val requestApp: String = "",
    @Json(name = "requestBase")
    val requestBase: String = "",
    @Json(name = "requestBaseUrl")
    val requestBaseUrl: String = "",
    @Json(name = "requestHash")
    val requestHash: String = "",
    @Json(name = "requestTime")
    val requestTime: Int = 0,
    @Json(name = "SESSION")
    val session: Session = Session(),
    @Json(name = "status")
    val status: Int = 0,
    @Json(name = "themeName")
    val themeName: String = ""
)