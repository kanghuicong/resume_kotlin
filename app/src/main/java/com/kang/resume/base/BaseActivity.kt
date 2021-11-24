package com.kang.resume.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.elvishew.xlog.XLog
import com.google.gson.Gson
import com.kang.resume.MainActivity
import com.kang.resume.MainApplication
import com.kang.resume.R
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.pro.IBaseBinding
import com.kang.resume.pro.IView
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.DataStoreUtils
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
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>() : AppCompatActivity(),IView,
    IBaseBinding<VB> {

    lateinit var mBinding: VB
    lateinit var mVm: VM
    lateinit var activity: AppCompatActivity

    @LayoutRes
    abstract fun initLayout(): Int

    abstract fun initViewModel(): VM

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        mBinding = DataBindingUtil.setContentView(this, initLayout())
        mVm = initViewModel()

        //初始化弹窗
        initPopup(activity,mVm,this)

        mVm.finishLiveData.observe(this, Observer {
            if (it!!) finish()
        })

        mBinding.lifecycleOwner = this
        mBinding.initBinding()
    }

    inline fun <reified T>formJson(): T{
        val bundle = intent.extras
        val data = bundle!!.get(RouterConfig.data)
        return Gson().fromJson(data as String)

    }

    inline fun <reified T : Any> Gson.fromJson(json: String): T {
        return Gson().fromJson(json, T::class.java)
    }


    override fun onDestroy() {
        // 在Activity销毁时记得解绑，以免内存泄漏
        mBinding.unbind()
        super.onDestroy()
    }
}
