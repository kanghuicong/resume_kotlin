package com.kang.resume.mine

import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.MineVipActivityBinding
import com.kang.resume.router.RouterConfig

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.MineVipRouter)
class MineVipActivity : BaseActivity<MineVipActivityBinding, MineVipViewModel>() {
    override fun initLayout(): Int {
        return R.layout.mine_vip_activity
    }

    override fun initViewModel(): MineVipViewModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, MineVipViewModel())
        return mBinding.vm!!
    }

    override fun MineVipActivityBinding.initBinding() {

    }
}