package com.anpe.coolbbsyou.net.model.feedList

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErshouInfo(
    @Json(name = "city")
    val city: String = "",
    @Json(name = "city_code")
    val cityCode: String = "",
    @Json(name = "country")
    val country: String = "",
    @Json(name = "dateline")
    val dateline: Int = 0,
    @Json(name = "deal_type")
    val dealType: Int = 0,
    @Json(name = "ershou_lastupdate")
    val ershouLastupdate: Int = 0,
    @Json(name = "ershou_product_id")
    val ershouProductId: Int = 0,
    @Json(name = "ershou_status")
    val ershouStatus: Int = 0,
    @Json(name = "ershou_type_id")
    val ershouTypeId: Int = 0,
    @Json(name = "exchange_price_type")
    val exchangePriceType: Int = 0,
    @Json(name = "feed_id")
    val feedId: Int = 0,
    @Json(name = "isAgreement")
    val isAgreement: Int = 0,
    @Json(name = "is_face_deal")
    val isFaceDeal: Int = 0,
    @Json(name = "is_link")
    val isLink: Int = 0,
    @Json(name = "link_source")
    val linkSource: String = "",
    @Json(name = "link_url")
    val linkUrl: String = "",
    @Json(name = "product_config_data")
    val productConfigData: String = "",
    @Json(name = "product_config_source")
    val productConfigSource: String = "",
    @Json(name = "product_logo")
    val productLogo: String = "",
    @Json(name = "product_price")
    val productPrice: Int = 0,
    @Json(name = "product_title")
    val productTitle: String = "",
    @Json(name = "province")
    val province: String = "",
    @Json(name = "store_type")
    val storeType: Int = 0,
    @Json(name = "store_type_txt")
    val storeTypeTxt: String = ""
)