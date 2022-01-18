package com.kang.resume.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.kang.resume.R
import com.kang.resume.custom.IBackClick
import com.kang.resume.custom.TitleView
import com.kang.resume.pro.IBaseBinding
import com.kang.resume.pro.IView
import com.kang.resume.router.RouterConfig
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.DelicateCoroutinesApi


/**
 * 类描述：
 * author:kanghuicong
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>() : AppCompatActivity(),
    IView,
    IBaseBinding<VB> {

    lateinit var mBinding: VB
    lateinit var mVm: VM
    lateinit var activity: AppCompatActivity

    @LayoutRes
    abstract fun initLayout(): Int

    abstract fun initViewModel(): VM

    open fun isInput(): Boolean {
        return false
    }

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        mBinding = DataBindingUtil.setContentView(this, initLayout())
        mVm = initViewModel()

        //初始化弹窗
        initPopup(activity, mVm, this)

        mVm.finishLiveData.observe(this, Observer {
            if (it!!) finish()
        })

        mBinding.lifecycleOwner = this
        mBinding.initBinding()

        val titleView = mBinding.root.findViewById<TitleView>(R.id.title_view)
        if (titleView != null) {
            titleView.iBackClick = (object : IBackClick {
                override fun click(view: View) {
                    if (isInput())
                        initInputDialog(activity)
                    else finish()
                }
            })
        }
    }

    inline fun <reified T> getData(): T? {
        val bundle = intent.extras
        val data = bundle!!.get(RouterConfig.data)
        if (data == null || data == "null") return null
        return Gson().fromJson(data as String)
    }

    inline fun <reified T> getOtherData(): T? {
        val bundle = intent.extras
        val data = bundle!!.get(RouterConfig.otherData)
        if (data == null || data == "null") return null
        return Gson().fromJson(data as String)
    }

    inline fun <reified T : Any> Gson.fromJson(json: String): T {
        return Gson().fromJson(json, T::class.java)
    }


    override fun onBackPressed() {
        if (isInput()) {
            initInputDialog(activity)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        // 在Activity销毁时记得解绑，以免内存泄漏
        mBinding.unbind()
        super.onDestroy()
    }
}
