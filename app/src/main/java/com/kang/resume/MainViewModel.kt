package com.kang.resume

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.base.ValuableConfig
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import com.kang.resume.utils.LocalDataUtils
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */

class MainViewModel : BaseViewModel() {

    var index = MutableLiveData(0)

    var drawableTemplate = EventMutableLiveData<Int>()
    var colorTemplate = EventMutableLiveData<Int>()
    var drawableResume = EventMutableLiveData<Int>()
    var colorResume = EventMutableLiveData<Int>()
    var drawableMine = EventMutableLiveData<Int>()
    var colorMine = EventMutableLiveData<Int>()

    var isLogin = EventMutableLiveData<Boolean>()

    init {
        drawableTemplate.value = R.mipmap.icon_select_0
        colorTemplate.value = R.color.primary
        drawableResume.value = R.mipmap.icon_disselect_1
        colorResume.value = R.color.black
        drawableMine.value = R.mipmap.icon_disselect_2
        colorMine.value = R.color.black

        viewModelScope.launch {
            isLogin.value = LocalDataUtils.geIsLogin()
        }
    }

    fun click(index: Int) {
        this.index.value = index
        if (index != 1 || isLogin.value == true) {

            drawableTemplate.value =
                if (index == 0) R.mipmap.icon_select_0 else R.mipmap.icon_disselect_0
            colorTemplate.value = if (index==0)R.color.primary else R.color.black

            drawableResume.value =
                if (index == 1) R.mipmap.icon_select_1 else R.mipmap.icon_disselect_1
            colorResume.value = if (index==1)R.color.primary else R.color.black

            drawableMine.value =
                if (index == 2) R.mipmap.icon_select_2 else R.mipmap.icon_disselect_2
            colorMine.value = if (index==2)R.color.primary else R.color.black

        }
    }


    fun queryResumeInfoList() {
        viewModelScope.launch {
            if (LocalDataUtils.geIsLogin())
                launchWithNoLoad(object : IViewModel<List<ResumeInfoBean>> {
                    override suspend fun launch(): ApiResponse<List<ResumeInfoBean>> {
                        return HttpRequest.api().queryResumeInfoList()
                    }
                }, (object : IHttp<List<ResumeInfoBean>> {
                    override suspend fun success(data: List<ResumeInfoBean>?) {
                        ValuableConfig.resumeInfoList = data
                    }

                    override suspend fun failure(response: ApiResponse<List<ResumeInfoBean>>) {}
                }))
        }
    }

}