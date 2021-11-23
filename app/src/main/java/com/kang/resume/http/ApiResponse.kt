package com.kang.resume.http

import com.google.gson.annotations.SerializedName

/**
 * 类描述：
 * author:kanghuicong
 */
data class ApiResponse<T>(
    @SerializedName("code")  var code: Int = -1,

    @SerializedName("message")  var message: String? = "",

    @SerializedName("data") val data: T? = null
)


data class BaseResponse(
    @SerializedName("code")  var code: Int = -1,

    @SerializedName("message")  var message: String? = "",
)