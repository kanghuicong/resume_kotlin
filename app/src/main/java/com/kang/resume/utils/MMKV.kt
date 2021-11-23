package com.roy.base.common

import android.content.Context
import android.os.Parcelable
import com.elvishew.xlog.XLog
import com.google.gson.Gson

import com.tencent.mmkv.MMKV
import java.io.Serializable
import java.lang.reflect.Type

class MMKVDb {

    companion object {

        const val MMKV_DB_NAME = "Resume"

        const val TOKEN_KEY = "TOKEN_KEY"


        val mmkv: MMKVDb by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MMKVDb()
        }


        /**
         * 在 Application初始化
         */
        fun init(context: Context) {
            MMKV.initialize(context)
        }
    }

    fun getDbName(): String {
        return MMKV_DB_NAME
    }


    /**
     * 配置 单独自己的实例
     */
    fun mmkv(mmkvName: String): MMKV? = MMKV.mmkvWithID(mmkvName)

/*    */
    /**
     * 默认实例
     *//*
    fun mmkv():MMKV?=MMKV.defaultMMKV()*/

    fun putString(key: String, value: String?) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun getString(key: String): String {
        return mmkv(getDbName())?.decodeString(key) ?: ""
    }

    fun putBoolean(key: String, value: Boolean) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun getBoolean(key: String): Boolean {
        return mmkv(getDbName())?.decodeBool(key) ?: false
    }

    fun putLong(key: String, value: Long) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun getLong(key: String): Long {
        return mmkv(getDbName())?.decodeLong(key) ?: 0
    }

    fun putDouble(key: String, value: Double) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun getDouble(key: String): Double {
        return mmkv(getDbName())?.decodeDouble(key) ?: 0.0
    }

    fun putFloat(key: String, value: Float) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun getFloat(key: String): Float {
        return mmkv(getDbName())?.decodeFloat(key) ?: 0F
    }

    fun putInt(key: String, value: Int) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun getInt(key: String): Int {
        return mmkv(getDbName())?.decodeInt(key) ?: 0
    }

    fun putParcelable(key: String, value: Parcelable) {
        mmkv(getDbName())?.encode(key, value)
    }

    fun <T : Parcelable?> getParcelable(
        key: String,
        tClass: Class<T>,
        defaultValue: T
    ): Parcelable? {
        return mmkv(getDbName())?.decodeParcelable(key, tClass, defaultValue)
    }

    /**
     *  mmkv 不支持Serializable  建议 把对象转成字节数组
     */
    fun putByteArray(key: String, value: ByteArray?) {
        mmkv(getDbName())?.encode(key, value)
    }


    fun getByteArray(key: String): ByteArray? {
        return mmkv(getDbName())?.decodeBytes(key, null)
    }


    /**
     *  序列化 对象
     */
    fun <T> putSerializableObject(key: String, obj: T) {
        val toByte = GsonUtils.gson.toJson(obj).toByteArray();
        putByteArray(key, toByte)
    }

    /**
     * 反序列化 对象
     */
    fun <T> getSerializableObject(key: String, tClass: Class<T>): T? {
        var byte: ByteArray? = getByteArray(key);
        byte?.let {
            return GsonUtils.gson.fromJson(String(byte), tClass);
        }
        return null;
    }

}

object GsonUtils {
    val gson = Gson();

    fun logToGson(body: Any?) {
        XLog.i("body:" + gson.toJson(body))
    }

    fun stringToGson(body: Any?): String? {
        return gson.toJson(body)
    }


    fun <T> fromJson(json: String? = "", classOfT: Class<T>): T? {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String? = "", typeOfT: Type): T? {
        return gson.fromJson(json, typeOfT)
    }
}