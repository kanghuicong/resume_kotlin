package com.kang.resume.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner


/**
 * 类描述：
 * author:kanghuicong
 */

object ViewModelProviderFactory {
    @JvmStatic
    fun <T> getViewModel(
        context: ViewModelStoreOwner?,
        model: ViewModel
    ): T {
        return ViewModelProvider(context!!, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return model as T
            }
        }).get(model::class.java) as T
    }
}