package com.kang.resume.login

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.LoginActivityBinding
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.VerifyUtils
import com.vondear.rxtool.view.RxToast

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.LoginRouter)
class LoginActivity : BaseActivity<LoginActivityBinding, LoginModel>() {

    override fun initLayout(): Int {
        return R.layout.login_activity
    }

    override fun LoginActivityBinding.initBinding() {

        mBinding.vm!!.isLogin.observe(activity, Observer {
            if (it!!) {
                mBinding.titleView.setTitle(getString(R.string.login))
            } else {
                mBinding.titleView.setTitle(getString(R.string.register))
            }
        })

        mBinding.etLoginAccount.setText("18979756586")
        mBinding.etLoginPassword.setText("123456")
        mBinding.btLogin.setOnClickListener {
            val account = mBinding.etLoginAccount.text.toString()
            val password = mBinding.etLoginPassword.text.toString()
            val repeatPassword = mBinding.etConfirmPassword.text.toString()
            if (!VerifyUtils.verifyPhone(account)) {
                RxToast.normal(getString(R.string.please_input_right_account))
                return@setOnClickListener
            }

            if (!VerifyUtils.verifyPassword(password)) {
                RxToast.normal(getString(R.string.please_input_right_password))
                return@setOnClickListener
            }

            if (mBinding.vm?.isLogin?.value == true) {
                mBinding.vm?.doLogin(account, password, needStart = true, needEnd = false)
            } else {
                if (!VerifyUtils.verifyEmpty(repeatPassword)) {
                    RxToast.normal(getString(R.string.please_input_confirm_password))
                    return@setOnClickListener
                }

                if (password != repeatPassword) {
                    RxToast.normal(getString(R.string.please_input_right_confirm_password))
                    return@setOnClickListener
                }

                mBinding.vm?.doRegister(account, password, repeatPassword)
            }
        }

    }

    override fun initViewModel(): LoginModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(activity, LoginModel())
        return mBinding.vm!!
    }

}