package com.kang.resume.mine

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.MineSettingActivityBinding
import com.kang.resume.event.LoginLiveData
import com.kang.resume.login.LoginModel
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.DataStoreUtils
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.MineSettingRouter)
class MineSettingActivity : BaseActivity<MineSettingActivityBinding, MineSettingModel>() {
    override fun initLayout(): Int {
        return R.layout.mine_setting_activity
    }

    override fun initViewModel(): MineSettingModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(activity, MineSettingModel())
        return mBinding.vm!!
    }

    override fun MineSettingActivityBinding.initBinding() {
        mBinding.btLogout.setOnClickListener {
            mBinding.vm?.logout()
        }

        LoginLiveData.getInstance().observe(activity, Observer {
            mVm.isLogin.value = it.isLogin
        })
    }

}