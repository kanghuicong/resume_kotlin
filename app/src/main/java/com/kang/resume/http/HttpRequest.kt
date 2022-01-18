package com.kang.resume.http

import android.util.Log
import com.google.gson.JsonParseException
import com.kang.resume.pro.IHttp
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.*
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * 类描述：
 * author:kanghuicong
 */
object HttpRequest {
    var apiService: ApiService? = null

    suspend fun api(): ApiService {
        if (apiService == null) newApi()
        return apiService!!
    }

    suspend fun newApi() {
        apiService = RetrofitClient.getService(ApiService::class.java)
    }

    //发起请求
    @JvmStatic
    suspend inline fun <T> doHttp(
        crossinline call: suspend CoroutineScope.() -> ApiResponse<T>,
        iHttp: IHttp<T>?
    ) {
        apiCall(call, iHttp)
    }


    suspend inline fun <T> apiCall(
        crossinline call: suspend CoroutineScope.() -> ApiResponse<T>,
        iHttp: IHttp<T>?,
    ) {
        withContext(Dispatchers.IO) {
            val res: ApiResponse<T>
            try {
                res = call()

                when (res.code) {
                    200 -> {
                        iHttp?.success(res.data)
                    }else -> {
                        iHttp?.failure(res)
                    }
                }
            } catch (e: Throwable) {
                // 请求出错，将状态码和消息封装为 ResponseResult
                Log.e("请求出错:", e.toString())
                iHttp?.failure(ApiException.build(e).toResponse<T>())
                return@withContext
            }
        }
    }


    // 网络、数据解析错误处理
    class ApiException(
        private val code: Int,
        override val message: String?,
        override val cause: Throwable? = null
    ) : RuntimeException(message, cause) {
        companion object {
            // 网络状态码
            private const val CODE_NET_ERROR = 4000
            private const val CODE_TIMEOUT = 4080
            private const val CODE_JSON_PARSE_ERROR = 4010
            private const val CODE_SERVER_ERROR = 5000

            // 业务状态码
            const val CODE_AUTH_INVALID = 401

            fun build(e: Throwable): ApiException {
                return if (e is HttpException) {
                    ApiException(CODE_NET_ERROR, "网络异常(${e.code()},${e.message()})")
                } else if (e is UnknownHostException) {
                    ApiException(CODE_NET_ERROR, "网络连接失败，请检查后再试")
                } else if (e is ConnectTimeoutException || e is SocketTimeoutException) {
                    ApiException(CODE_TIMEOUT, "请求超时，请稍后再试")
                } else if (e is IOException) {
                    ApiException(CODE_NET_ERROR, "网络异常(${e.message})")
                } else if (e is JsonParseException || e is JSONException) {
                    // Json解析失败
                    ApiException(CODE_JSON_PARSE_ERROR, "数据解析错误，请稍后再试")
                } else {
                    ApiException(CODE_SERVER_ERROR, "系统错误(${e.message})")
                }
            }
        }

        fun <T> toResponse(): ApiResponse<T> {
            return ApiResponse(code, message)
        }

    }

}

