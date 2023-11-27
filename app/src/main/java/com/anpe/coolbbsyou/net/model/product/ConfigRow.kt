package com.anpe.coolbbsyou.net.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigRow(
    @Json(name = "cpu")
    val cpu: String = "",
    @Json(name = "fingerpoint_type")
    val fingerpointType: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "phone_material")
    val phoneMaterial: String = "",
    @Json(name = "price")
    val price: Int = 0,
    @Json(name = "product_id")
    val productId: Int = 0,
    @Json(name = "ram")
    val ram: String = "",
    @Json(name = "screen_material")
    val screenMaterial: String = "",
    @Json(name = "title")
    val title: String = ""
)