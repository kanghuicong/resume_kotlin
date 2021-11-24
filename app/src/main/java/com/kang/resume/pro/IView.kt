package com.kang.resume.pro

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.LocalDataUtils
import com.kang.resume.utils.ToastUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.LoadingPopupView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */
interface IView {

    @DelicateCoroutinesApi
    fun initPopup(activity: Activity, mVm: BaseViewModel, owner: LifecycleOwner) {
        val loadingPopup = XPopup.Builder(activity)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(false)
            .isLightNavigationBar(true)
            .asLoading(activity.getString(R.string.loading))

        val loginExpiredPopup = XPopup.Builder(activity)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asConfirm(
                activity.getString(R.string.tip), activity.getString(R.string.tip_expired)
            ) {
                RouterNavigation.doIntentActivity(RouterConfig.LoginRouter)
            }

        //loading弹窗
        mVm.loadingLiveData.observe(owner, Observer {
            if (it!!) {
                loadingPopup?.show()
            } else {
                loadingPopup?.dismiss()
            }
        })
        //错误弹窗
        mVm.loginLiveData.observe(owner, Observer { it ->
            when (it!!.code) {
                401 -> {
                    GlobalScope.launch {
                        LocalDataUtils.logout()
                        //通知页面更新
                        LoginLiveData.getInstance().postValue(LoginBean(false))
                    }

                    if (!loadingPopup!!.isDismiss) {
                        loadingPopup.dismissWith {
                            loginExpiredPopup?.show()
                        }
                    } else {
                        loginExpiredPopup?.show()
                    }
                }
                else -> {
                    val errorPopup = XPopup.Builder(activity)
                        .dismissOnBackPressed(false)
                        .isDestroyOnDismiss(true)
                        .dismissOnTouchOutside(false)
                        .asConfirm(
                            activity.getString(R.string.tip), it.message
                        ) {}

                    errorPopup.show()
                }
            }

            mVm.toastLiveData.observe(owner, Observer {
                if (it is Int) ToastUtil.show(activity.getString(it))
                if (it is String) ToastUtil.show(it)
            })
        })
    }
}