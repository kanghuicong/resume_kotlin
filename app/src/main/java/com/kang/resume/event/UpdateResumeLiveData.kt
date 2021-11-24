package com.kang.resume.event

import androidx.lifecycle.MutableLiveData

/**
 * 类描述：
 * author:kanghuicong
 */
class UpdateResumeLiveData : MutableLiveData<Boolean>() {

    private object Holder {
        val INSTANCE: UpdateResumeLiveData = UpdateResumeLiveData()
    }

    companion object {
        fun getInstance(): UpdateResumeLiveData {
            return Holder.INSTANCE
        }
    }
}