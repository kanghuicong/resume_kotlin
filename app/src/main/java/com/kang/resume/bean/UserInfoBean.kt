package com.kang.resume.bean

import com.google.gson.annotations.SerializedName

/**
 * 类描述：
 * author:kanghuicong
 */
data class UserInfoBean(
    @SerializedName("account") var account: String = "",

    @SerializedName("is_vip") var isVip: Boolean = false,

    @SerializedName("vip_time") var vipTime: String = ""

)