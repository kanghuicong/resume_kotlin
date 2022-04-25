package com.kang.resume.bean

import com.google.gson.annotations.SerializedName

/**
 * 类描述：
 * author:kanghuicong
 */
data class VipBean(
    @SerializedName("configId") var configId: Int = -1,
    @SerializedName("discountEndTime") var discountEndTime: String = "",
    @SerializedName("discountPrice") var discountPrice: Int = 0,
    @SerializedName("discountStartTime") var discountStartTime: String = "",
    @SerializedName("periodValidity") var periodValidity: Int = 0,
    @SerializedName("price") var price: Int = 0,
    var isSelected: Boolean = false,
)