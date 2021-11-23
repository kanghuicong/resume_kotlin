package com.kang.resume.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.utils.LocalDataUtils
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */
class MineSettingModel : BaseViewModel() {

    var isLogin = EventMutableLiveData<Boolean>()

    init {
        isLogin.value = false
        viewModelScope.launch {
            isLogin.value = LocalDataUtils.geIsLogin()
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