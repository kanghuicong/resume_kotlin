package com.kang.resume.http

import android.text.format.DateUtils
import android.util.Log
import com.elvishew.xlog.XLog
import com.kang.resume.BuildConfig
import com.kang.resume.base.ValueConfig
import com.kang.resume.utils.DataStoreUtils
import com.kang.resume.utils.LocalDataUtils
import com.roy.base.common.MMKVDb
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 类描述：
 * author:kanghuicong
 */
object RetrofitClient {
    private const val TIME_OUT = 5


    private suspend fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        builder.addInterceptor(logging)
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

        //取token
        val token = LocalDataUtils.getToken()

        XLog.e("token:$token")

        builder.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
//                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("token", token)
                .build()
            chain.proceed(request);

        }

        return builder.build()
    }

    suspend fun <Service> getService(serviceClass: Class<Service>): Service {

        return Retrofit.Builder()
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ValueConfig.BASE_URL)
            .build()
            .create(serviceClass)
    }


}


