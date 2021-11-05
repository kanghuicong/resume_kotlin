package com.kang.resume

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.MainActivityBinding
import com.kang.resume.mine.MineFragment
import com.kang.resume.resume.ResumeInfoFragment
import com.kang.resume.template.TemplateMallFragment
import java.util.*

class MainActivity : BaseActivity<MainActivityBinding>() {

    var templateMallFragment: TemplateMallFragment? = null
    var resumeInfoFragment: ResumeInfoFragment? = null
    var mineFragment: MineFragment? = null
    lateinit var fm: FragmentManager


    override fun initLayout(): Int {
        return R.layout.main_activity
    }

    override fun MainActivityBinding.initBinding() {
        initialize()
    }

    //初始化
    private fun initialize() {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, MainViewModel())

        fm = supportFragmentManager

        templateMallFragment = TemplateMallFragment()

        //展示首页
        fm.beginTransaction().replace(R.id.fl_main, templateMallFragment!!)
            .addToBackStack(null)
            .commit()

        //点击事件
        mBinding.vm?.index?.observe(this, Observer<Int> {
            val transaction = fm.beginTransaction()
            if (templateMallFragment != null)
                transaction.hide(templateMallFragment!!)
            if (resumeInfoFragment != null)
                transaction.hide(resumeInfoFragment!!)
            if (mineFragment != null)
                transaction.hide(mineFragment!!)

            when (it) {
                0 -> {
                    if (templateMallFragment == null) {
                        templateMallFragment = TemplateMallFragment()
                        transaction.add(
                            R.id.fl_main,
                            templateMallFragment!!
                        )
                    } else {
                        transaction.show(templateMallFragment!!)
                    }
                }
                1 -> {
                    if (resumeInfoFragment == null) {
                        resumeInfoFragment = ResumeInfoFragment()
                        transaction.add(
                            R.id.fl_main,
                            resumeInfoFragment!!
                        )
                    } else {
                        transaction.show(resumeInfoFragment!!)
                    }
                }
                2 -> {
                    if (mineFragment == null) {
                        mineFragment = MineFragment()
                        transaction.add(
                            R.id.fl_main,
                            mineFragment!!
                        )
                    } else {
                        transaction.show(mineFragment!!)
                    }
                }
            }

            transaction.commitAllowingStateLoss()
        })
    }

}



