package com.kang.resume.login

import androidx.lifecycle.MutableLiveData
import com.elvishew.xlog.XLog
import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.UserInfoBean
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import com.kang.resume.utils.LocalDataUtils

/**
 * 类描述：
 * author:kanghuicong
 */
class LoginModel : BaseViewModel() {

    var isLogin = EventMutableLiveData<Boolean>()

    val hintText = EventMutableLiveData<String>()
    val btText = EventMutableLiveData<String>()

    init {
        isLogin.value = true
        btText.value = getString(R.string.login)
        hintText.value = getString(R.string.register)
    }

    fun doSwitch() {
        if (isLogin.value == true) {
            btText.value = getString(R.string.register)
            hintText.value = getString(R.string.login)
            isLogin.value = false
        } else {
            btText.value = getString(R.string.login)
            hintText.value = getString(R.string.register)
            isLogin.value = true
        }
    }

    //登录
    fun doLogin(
        account: String,
        password: String,
        needStart: Boolean = true,
        needEnd: Boolean = true,

        ) {
        launch(
            object : IViewModel<String> {
                override suspend fun launch(): ApiResponse<String> {
                    return HttpRequest.api().login(account, password)
                }
            },
            (object : IHttp<String> {
                override suspend fun success(data: String?) {
                    data?.apply {
                        LocalDataUtils.setToken(data).apply {
                            getUserInfo()
                        }
                    }
                }

                override suspend fun failure(response: ApiResponse<String>) {}
            }),
            needStart,
            needEnd
        )

    }

    //注册
    fun doRegister(account: String, password: String, repeatPassword: String) {
        launchWithStartLoad(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().register(account, password, repeatPassword)
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                //注册成功立即登录
                doLogin(account, password, needStart = false, needEnd = false)
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }


    //获取用户信息
    private fun getUserInfo() {
        launchWithEndLoad(object : IViewModel<UserInfoBean> {
            override suspend fun launch(): ApiResponse<UserInfoBean> {
                return HttpRequest.api().queryAccountInfo()
            }
        }, (object : IHttp<UserInfoBean> {
            override suspend fun success(data: UserInfoBean?) {
                toastLiveData.postValue(R.string.login_success)

                data?.apply {
                    //本地数据储存
                    LocalDataUtils.login(data)
                    //通知"我的""设置"页面更新页面
                    LoginLiveData.getInstance().postValue(LoginBean(true, data))
                }

                //关闭当前页
                finishLiveData.postValue(true)
            }

            override suspend fun failure(response: ApiResponse<UserInfoBean>) {}
        }))

    }
}