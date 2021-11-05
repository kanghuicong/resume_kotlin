package com.kang.resume.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


/**
 * 类描述：
 * author:kanghuicong
 */
abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    lateinit var mBinding: V

    lateinit var activity: Activity

    protected abstract fun initLayout(): Int

    protected abstract fun initViewCreated(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, initLayout(), container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewCreated(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as Activity
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}