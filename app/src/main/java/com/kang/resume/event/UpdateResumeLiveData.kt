package com.kang.resume.event

import androidx.lifecycle.MutableLiveData
import com.kang.resume.base.EventMutableLiveData

/**
 * 类描述：
 * author:kanghuicong
 */
class UpdateResumeLiveData : EventMutableLiveData<Boolean>() {

    private object Holder {
        val INSTANCE: UpdateResumeLiveData = UpdateResumeLiveData()
    }

    companion object {
        fun getInstance(): UpdateResumeLiveData {
            return Holder.INSTANCE
        }
    }
}