package com.kang.resume.bean

import android.content.Context
import android.util.LruCache
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import java.lang.reflect.Type
import java.util.*


/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = "/service/json")
class JsonServiceImpl : SerializationService {
    private var mGson: Gson? = null
    override fun init(context: Context?) {
        mGson = Gson()
    }

    override fun <T> json2Object(text: String?, clazz: Class<T>?): T {
        checkJson()
        return mGson!!.fromJson(text, clazz)
    }

    override fun object2Json(instance: Any?): String {
        checkJson()
        return mGson!!.toJson(instance)
    }

    override fun <T> parseObject(input: String?, clazz: Type?): T {
        checkJson()
        return mGson!!.fromJson(input, clazz)
    }

    fun checkJson() {
        if (mGson == null) {
            mGson = Gson()
        }
    }

//    private val cacheInstance by lazy {
//        val maxMemory = Runtime.getRuntime().maxMemory()
//        val cacheSize = (maxMemory / 8).toInt()
//        LruCache<String, Any>(cacheSize)
//    }
//
//    override fun init(context: Context)
//    {
//
//    }
//
//    override fun object2Json(instance: Any): String
//    {
//        val uuid = UUID.randomUUID().toString()
//        cacheInstance.put(uuid, instance)
//        return uuid
//    }
//
//    override fun <T : Any> json2Object(input: String, clazz: Class<T>): T = parseObject(input, clazz)
//
//    override fun <T : Any> parseObject(uuid: String, clazz: Type): T
//    {
//        val obj = cacheInstance.get(uuid)
//        cacheInstance.remove(uuid)
//        return obj as T
//    }
}