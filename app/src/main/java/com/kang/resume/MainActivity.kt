package com.kang.resume

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.BaseFragment
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.MainActivityBinding
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.mine.MineFragment
import com.kang.resume.resume.ResumeInfoFragment
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.template.TemplateMallFragment
import com.kang.resume.utils.LocalDataUtils
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>() {

    var templateMallFragment: TemplateMallFragment? = null
    var resumeInfoFragment: ResumeInfoFragment? = null
    var mineFragment: MineFragment? = null
    lateinit var fm: FragmentManager

    override fun initLayout(): Int {
        return R.layout.main_activity
    }

    @DelicateCoroutinesApi
    override fun MainActivityBinding.initBinding() {
        //监听登录状态
        LoginLiveData.getInstance().observe(activity, Observer {
            mVm.isLogin.value = it.isLogin
        })

        initialize()
    }

    //初始化
    @DelicateCoroutinesApi
    private fun initialize() {

        fm = supportFragmentManager

        templateMallFragment = TemplateMallFragment()

        //展示首页
        fm.beginTransaction().replace(R.id.fl_main, templateMallFragment!!)
            .addToBackStack(null)
            .commit()

        //点击事件
        mVm.index.observe(this, Observer {
            when (it) {
                0 -> {
                    val transaction = getTransaction()
                    if (templateMallFragment == null) {
                        templateMallFragment = TemplateMallFragment()
                        transaction.add(
                            R.id.fl_main,
                            templateMallFragment!!
                        )
                    } else {
                        transaction.show(templateMallFragment!!)
                    }
                    transaction.commitAllowingStateLoss()
                }
                1 -> {
                    if (mVm.isLogin.value == true) {
                        val transaction = getTransaction()
                        if (resumeInfoFragment == null) {
                            resumeInfoFragment = ResumeInfoFragment()
                            transaction.add(
                                R.id.fl_main,
                                resumeInfoFragment!!
                            )
                        } else {
                            transaction.show(resumeInfoFragment!!)
                        }
                        transaction.commitAllowingStateLoss()
                    } else {
                        XPopup.Builder(this)
                            .dismissOnBackPressed(false)
                            .dismissOnTouchOutside(false)
                            .asConfirm(
                                getString(R.string.tip), getString(R.string.tip_login)
                            ) {
                                GlobalScope.launch {
                                    LocalDataUtils.logout()
                                    //通知页面更新
                                    LoginLiveData.getInstance().postValue(LoginBean(false))
                                }
                                RouterNavigation.doIntentActivity(RouterConfig.LoginRouter)
                            }.show()
                    }
                }
                2 -> {
                    val transaction = getTransaction()
                    if (mineFragment == null) {
                        mineFragment = MineFragment()
                        transaction.add(
                            R.id.fl_main,
                            mineFragment!!
                        )
                    } else {
                        transaction.show(mineFragment!!)
                    }
                    transaction.commitAllowingStateLoss()
                }
            }


        })

        mVm.queryResumeInfoList()
    }

    override fun initViewModel(): MainViewModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, MainViewModel())
        return mBinding.vm!!
    }

    private fun getTransaction(): FragmentTransaction {
        val transaction = fm.beginTransaction()
        if (templateMallFragment != null)
            transaction.hide(templateMallFragment!!)
        if (resumeInfoFragment != null)
            transaction.hide(resumeInfoFragment!!)
        if (mineFragment != null)
            transaction.hide(mineFragment!!)
        return transaction
    }


}



