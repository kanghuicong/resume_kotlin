package com.kang.resume.mine

import android.content.Context
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.vondear.rxtool.RxDeviceTool

/**
 * 类描述：
 * author:kanghuicong
 */
class MineAboutUsModel(context: Context) : BaseViewModel() {
    var version = EventMutableLiveData<String>()

    var qq = "123456789"

    init {
        version.value ="v "+ RxDeviceTool.getAppVersionName(context)
    }

}