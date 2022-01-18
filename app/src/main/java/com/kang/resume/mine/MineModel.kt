package com.kang.resume.mine

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.LocalDataUtils
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */

class MineModel() : BaseViewModel() {

    var name = EventMutableLiveData<String>()
    var isLogin = EventMutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            isLogin.value = LocalDataUtils.geIsLogin()
            if (LocalDataUtils.getName() == "") {
                name.value = getString(R.string.login)
            } else {
                name.value = LocalDataUtils.getName()
            }
        }
    }


    fun doLogin() {
        if (!isLogin.value!!) {
            RouterNavigation.doIntentActivity(RouterConfig.LoginRouter)
        }

    }

    fun logout() {
        viewModelScope.launch {
            //清除本地数据
            LocalDataUtils.logout()
            //通知页面更新
            LoginLiveData.getInstance().postValue(LoginBean(false))
            finishLiveData.postValue(true)
        }
    }

}