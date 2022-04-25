package com.kang.resume.mine

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.VipBean
import com.kang.resume.custom.VipPopup
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.LocalDataUtils
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.launch


/**
 * 类描述：
 * author:kanghuicong
 */

class MineModel(var context: Context) : BaseViewModel() {

    var name = EventMutableLiveData<String>()
    var isLogin = EventMutableLiveData<Boolean>()
    var vipList: List<VipBean>? = null

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

    //vip
    fun doVip() {
        if (vipList == null) {
            launch(object : IViewModel<List<VipBean>> {
                override suspend fun launch(): ApiResponse<List<VipBean>> {
                    return HttpRequest.api().queryVipPriceConfigList()
                }
            }, (object : IHttp<List<VipBean>> {
                override suspend fun success(data: List<VipBean>?) {
                    vipList = data
                    if (data != null) {
                        showDialog(data)
                    }
                }

                override suspend fun failure(response: ApiResponse<List<VipBean>>) {}
            }))
        } else {
            showDialog(vipList!!)
        }
    }

    //显示vip
    private fun showDialog(list: List<VipBean>) {
        XPopup.Builder(context)
            .asCustom(
                VipPopup(
                    context, list,
                    (object : VipPopup.IClick {
                        override fun click(configId: Int) {
                            launch(object : IViewModel<Any> {
                                override suspend fun launch(): ApiResponse<Any> {
                                    return HttpRequest.api().getPayParams(configId)
                                }
                            }, (object : IHttp<Any> {
                                override suspend fun success(data: Any?) {

                                }

                                override suspend fun failure(response: ApiResponse<Any>) {}
                            }))
                        }
                    }),
                )
            )
            .show()
    }

}