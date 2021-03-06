package com.kang.resume.event

import androidx.lifecycle.MutableLiveData
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.UserInfoBean

/**
 * 类描述：
 * author:kanghuicong
 */
class LoginLiveData : EventMutableLiveData<LoginBean>() {

    private object Holder {
        val INSTANCE: LoginLiveData = LoginLiveData()
    }

    companion object {
        fun getInstance(): LoginLiveData {
            return Holder.INSTANCE
        }
    }
}

data class LoginBean(
    var isLogin: Boolean = false,
    var userInfoBean: UserInfoBean? = null,
)