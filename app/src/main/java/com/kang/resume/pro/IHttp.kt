package com.kang.resume.pro

import com.kang.resume.http.ApiResponse

/**
 * 类描述：
 * author:kanghuicong
 */

interface IHttp<T> {
    suspend fun success(data: T?)
    suspend fun failure(response: ApiResponse<T>)
}