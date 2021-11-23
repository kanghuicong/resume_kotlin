package com.kang.resume.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.elvishew.xlog.XLog
import com.kang.resume.bean.UserInfoBean
import com.kang.resume.event.LoginBean
import com.kang.resume.http.HttpRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * 类描述：
 * author:kanghuicong
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Resume")

object DataStoreUtils {

    lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.dataStore
    }

    suspend fun <T : Any> put(key: String, value: T) {
        dataStore.edit { setting ->
            when (value) {
                is Int -> setting[intPreferencesKey(key)] = value
                is Long -> setting[longPreferencesKey(key)] = value
                is Double -> setting[doublePreferencesKey(key)] = value
                is Float -> setting[floatPreferencesKey(key)] = value
                is Boolean -> setting[booleanPreferencesKey(key)] = value
                is String -> setting[stringPreferencesKey(key)] = value
                else -> throw IllegalArgumentException("This type can be saved into DataStore")
            }
        }
    }

    suspend inline fun <reified T : Any> get(key: String): T {
        return when (T::class) {
            Int::class -> {
                dataStore.data.map { setting ->
                    setting[intPreferencesKey(key)] ?: 0
                }.first() as T
            }
            Long::class -> {
                dataStore.data.map { setting ->
                    setting[longPreferencesKey(key)] ?: 0L
                }.first() as T
            }
            Double::class -> {
                dataStore.data.map { setting ->
                    setting[doublePreferencesKey(key)] ?: 0.0
                }.first() as T
            }
            Float::class -> {
                dataStore.data.map { setting ->
                    setting[floatPreferencesKey(key)] ?: 0f
                }.first() as T
            }
            Boolean::class -> {
                dataStore.data.map { setting ->
                    setting[booleanPreferencesKey(key)] ?: false
                }.first() as T
            }
            String::class -> {
                dataStore.data.map { setting ->
                    setting[stringPreferencesKey(key)] ?: ""
                }.first() as T
            }
            else -> {
                throw IllegalArgumentException("This type can be get into DataStore")
            }
        }
    }

}

object LocalDataUtils {

    suspend fun logout() {
        isLogon(false)
        setName("")
        setToken("")
    }

    suspend fun login(userInfoBean: UserInfoBean) {
        isLogon(true)
        setName(userInfoBean.account)
    }

    //token
    suspend fun setToken(token: String) {
        DataStoreUtils.put("TOKEN", token)
        //token变化后，apiService不会重新实例化加载新的token，手动重新实例化
        HttpRequest.newApi()
    }

    suspend fun getToken(): String {
        return DataStoreUtils.get("TOKEN")
    }

    //姓名
    private suspend fun setName(name: String) {
        DataStoreUtils.put("NAME", name)
    }

    suspend fun getName(): String {
        return DataStoreUtils.get("NAME")
    }

    //是否登录
    private suspend fun isLogon(isLogon: Boolean) {
        DataStoreUtils.put("IS_LOGIN", isLogon)
    }

    suspend fun geIsLogin(): Boolean {
        return DataStoreUtils.get("IS_LOGIN")
    }

}