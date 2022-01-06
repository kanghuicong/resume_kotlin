package com.kang.resume

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.kang.resume.utils.DataStoreUtils
import com.roy.base.common.MMKVDb
import com.vondear.rxtool.RxTool


/**
 * 类描述：
 * author:kanghuicong
 */
class MainApplication : Application() {
    companion object {
        private lateinit var application: Application

        fun getApplication(): Application {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        // 必须在初始化ARouter之前配置
        if (BuildConfig.DEBUG) {
            // 日志开启
            ARouter.openLog()
            // 调试模式开启，如果在install run模式下运行，则必须开启调试模式
            ARouter.openDebug()
        }
        ARouter.init(this)

        MMKVDb.init(this)
        DataStoreUtils.init(this)
        //日志工具
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)

        //RxTool工具类初始化
        RxTool.init(getApplication())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}