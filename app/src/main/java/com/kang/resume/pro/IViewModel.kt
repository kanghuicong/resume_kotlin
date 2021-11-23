package com.kang.resume.pro

import com.kang.resume.http.ApiResponse
import kotlinx.coroutines.CoroutineScope

/**
 * 类描述：
 * author:kanghuicong
 */
 interface IViewModel<T> {
    suspend fun launch(): ApiResponse<T>
 }
