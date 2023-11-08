package com.anpe.coolbbsyou.intent.state.global

data class GlobalState(
    var isNineGrid: Boolean = false,
    var isLogin: Boolean = false,
    var requestHash: String = "",
    var isShow: Boolean = false,
    var imageArray: ImageArray = ImageArray(),
    var imageArrayType: ImageArrayType = if (isNineGrid) ImageArrayType.NineGrid else ImageArrayType.ImageRow
)

data class ImageArray(
    val initialCount: Int = 0,
    val picArray: List<String> = listOf()
)

enum class ImageArrayType{
    NineGrid,
    ImageRow
}