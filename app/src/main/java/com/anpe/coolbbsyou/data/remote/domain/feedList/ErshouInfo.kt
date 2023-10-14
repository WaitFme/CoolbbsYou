package com.anpe.coolbbsyou.data.remote.domain.feedList


import com.google.gson.annotations.SerializedName

data class ErshouInfo(
    @SerializedName("city")
    val city: String = "",
    @SerializedName("city_code")
    val cityCode: String = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("dateline")
    val dateline: Int = 0,
    @SerializedName("deal_type")
    val dealType: Int = 0,
    @SerializedName("ershou_lastupdate")
    val ershouLastupdate: Int = 0,
    @SerializedName("ershou_product_id")
    val ershouProductId: Int = 0,
    @SerializedName("ershou_status")
    val ershouStatus: Int = 0,
    @SerializedName("ershou_type_id")
    val ershouTypeId: Int = 0,
    @SerializedName("exchange_price_type")
    val exchangePriceType: Int = 0,
    @SerializedName("feed_id")
    val feedId: Int = 0,
    @SerializedName("isAgreement")
    val isAgreement: Int = 0,
    @SerializedName("is_face_deal")
    val isFaceDeal: Int = 0,
    @SerializedName("is_link")
    val isLink: Int = 0,
    @SerializedName("link_source")
    val linkSource: String = "",
    @SerializedName("link_url")
    val linkUrl: String = "",
    @SerializedName("product_config_data")
    val productConfigData: String = "",
    @SerializedName("product_config_source")
    val productConfigSource: String = "",
    @SerializedName("product_logo")
    val productLogo: String = "",
    @SerializedName("product_price")
    val productPrice: Int = 0,
    @SerializedName("product_title")
    val productTitle: String = "",
    @SerializedName("province")
    val province: String = "",
    @SerializedName("store_type")
    val storeType: Int = 0,
    @SerializedName("store_type_txt")
    val storeTypeTxt: String = ""
)