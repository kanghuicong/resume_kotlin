package com.kang.resume.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kang.resume.MainViewModel
import com.kang.resume.pro.IBaseBinding
import java.lang.reflect.ParameterizedType

/**
 * 类描述：
 * author:kanghuicong
 */
abstract class BaseActivity<VB : ViewDataBinding>() : AppCompatActivity(),
    IBaseBinding<VB> {

    lateinit var mBinding: VB

    @LayoutRes
    abstract fun initLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, initLayout())

        mBinding.lifecycleOwner =this

        mBinding.initBinding()
    }


    override fun onDestroy() {
        super.onDestroy()
        // 在Activity销毁时记得解绑，以免内存泄漏
        mBinding.unbind()
    }
}
