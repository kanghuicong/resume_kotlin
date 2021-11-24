package com.kang.resume.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.kang.resume.R
import com.kang.resume.pro.IView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import kotlinx.coroutines.DelicateCoroutinesApi


/**
 * 类描述：
 * author:kanghuicong
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(),IView {

    lateinit var mBinding: V

    lateinit  var mVm: VM

    lateinit var activity: AppCompatActivity


    @LayoutRes
    abstract fun initLayout(): Int

    abstract fun initViewCreated(view: View)

    abstract fun initViewModel(): VM

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, initLayout(), container, false)
        mVm = initViewModel()
        //初始化弹窗
        initPopup(activity,mVm,this)

        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewCreated(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as AppCompatActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}