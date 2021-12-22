package com.kang.resume.pro

import android.view.View

/**
 * 类描述：
 * author:kanghuicong
 */
interface IWidget<T> {
    fun setData(data: T?)

    fun getView(): View

}
