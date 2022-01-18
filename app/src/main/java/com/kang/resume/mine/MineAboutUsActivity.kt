package com.kang.resume.mine

import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.custom.ItemView
import com.kang.resume.databinding.MineAboutUsBinding
import com.kang.resume.router.RouterConfig
import com.vondear.rxtool.RxClipboardTool
import com.vondear.rxtool.view.RxToast

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.MineAboutUsRouter)
class MineAboutUsActivity : BaseActivity<MineAboutUsBinding, MineAboutUsModel>() {
    override fun initLayout(): Int {
        return R.layout.mine_about_us
    }

    override fun MineAboutUsBinding.initBinding() {
        mBinding.itemQq.setTip(
            String.format(
                getString(R.string.service_qq_detail_with_copy),
                mVm.qq
            )
        )
        mBinding.itemQq.setItemClick(object : ItemView.IItemClick {
            override fun click() {
                RxClipboardTool.copyText(activity, mVm.qq)
                RxToast.success(getString(R.string.copy_success))
            }
        })

    }

    override fun initViewModel(): MineAboutUsModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, MineAboutUsModel(this))
        return mBinding.vm!!
    }


}