package com.kang.resume.pro

import androidx.databinding.ViewDataBinding

/**
 * 类描述：
 * author:kanghuicong
 */
interface IBaseBinding<VB : ViewDataBinding> {

    fun VB.initBinding()
}