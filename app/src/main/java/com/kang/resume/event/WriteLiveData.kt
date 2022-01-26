package com.kang.resume.event

import androidx.lifecycle.MutableLiveData
import com.kang.resume.base.EventMutableLiveData

/**
 * 类描述：
 * author:kanghuicong
 */
class WriteLiveData : EventMutableLiveData<WriteLiveBean>() {

    private object Holder {
        val INSTANCE: WriteLiveData = WriteLiveData()
    }

    companion object {
        fun getInstance(): WriteLiveData {
            return Holder.INSTANCE
        }
    }
}

data class WriteLiveBean(
    var from: String? = null,
    var content: String = "",
)