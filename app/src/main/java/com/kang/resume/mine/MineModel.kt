package com.kang.resume.mine

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kang.resume.R

/**
 * 类描述：
 * author:kanghuicong
 */

class MineModel(activity: Activity) : ViewModel() {

    var name = MutableLiveData<String>()
    var isLogin = MutableLiveData<Boolean>()

    init {
        name.value = activity.getString(R.string.login)
        isLogin.value = false
    }


    fun doLogin() {
        println("----2----${isLogin.value}----${name.value}")
        if (!isLogin.value!!){
            println("----3----")
            name.value = "Test"
            isLogin.value = true
        }

    }

}