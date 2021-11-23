package com.kang.resume.mine

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.kang.resume.R
import com.kang.resume.base.BaseFragment
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.UserInfoBean
import com.kang.resume.databinding.MineFragmentBinding
import com.kang.resume.event.LoginLiveData
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.DataStoreUtils
import com.kang.resume.utils.LocalDataUtils
import kotlinx.coroutines.launch


/**
 * 类描述：
 * author:kanghuicong
 */
class MineFragment : BaseFragment<MineFragmentBinding, MineModel>(), View.OnClickListener {

    override fun initLayout(): Int {
        return R.layout.mine_fragment
    }

    override fun initViewCreated(view: View) {
        mBinding.itemSetting.setOnClickListener(this)
        mBinding.tvMineName.setOnClickListener(this)

        //监听登录状态
        LoginLiveData.getInstance().observe(this, Observer {
            mVm.isLogin.value = it.isLogin
            mVm.viewModelScope.launch {
                if (it.isLogin) {
                    mVm.name.value = it.userInfoBean?.account
                } else {
                    mVm.name.value = getString(R.string.login)
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.item_setting -> {
                RouterNavigation.doIntentActivity(RouterConfig.MineSettingRouter)
            }
            R.id.tv_mine_name -> {
                mVm.doLogin()
            }
        }

    }

    override fun initViewModel(): MineModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, MineModel())
        return mBinding.vm!!
    }


}