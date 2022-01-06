package com.kang.resume.router

import com.alibaba.android.arouter.launcher.ARouter
import com.kang.resume.bean.ResumeInfoBean

/**
 * 类描述：
 * author:kanghuicong
 */
object RouterNavigation {

    //无参跳转
    fun doIntentActivity(path: String) {
        ARouter.getInstance()
            .build(path)
            .navigation()
    }

    //带参跳转
    fun doIntentActivity(path: String, data: ResumeInfoBean) {
        ARouter.getInstance()
            .build(path)
            .withObject(RouterConfig.data, data)
            .navigation()
    }

    //带参跳转
    fun doIntentActivity(path: String, from: String, data: ResumeInfoBean) {
        ARouter.getInstance()
            .build(path)
            .withObject(RouterConfig.data, data)
            .withString(RouterConfig.from, from)
            .navigation()
    }

    //带参跳转
    fun doIntentActivity(path: String, data: ResumeInfoBean?, otherData: Any?) {
        ARouter.getInstance()
            .build(path)
            .withObject(RouterConfig.data, data)
            .withObject(RouterConfig.otherData, otherData)
            .navigation()
    }

    //带参跳转
    fun doIntentActivity(path: String, from: String, data: ResumeInfoBean?, otherData: Any?) {
        ARouter.getInstance()
            .build(path)
            .withString(RouterConfig.from, from)
            .withObject(RouterConfig.data, data)
            .withObject(RouterConfig.otherData, otherData)
            .navigation()
    }
}