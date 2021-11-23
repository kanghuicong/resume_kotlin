package com.kang.resume.resume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvishew.xlog.XLog
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.ValuableConfig
import com.kang.resume.bean.ResumeInfoBean
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
class ResumeInfoModel : BaseViewModel() {

    var resumeInfoList = MutableLiveData<List<ResumeInfoBean>>()
    var index = 0
    var resumeInfo = ResumeInfoBean()

    init {
        if (ValuableConfig.resumeInfoList == null) {
            queryResumeInfoList()
        } else {
            resumeInfoList.value = ValuableConfig.resumeInfoList
            initResume()
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
                        resumeInfoList.postValue(data)

                        initResume()
                    }

                    override suspend fun failure(response: ApiResponse<List<ResumeInfoBean>>) {}
                }))
        }
    }

    fun initResume() {
        if (resumeInfoList.value != null && resumeInfoList.value!!.isNotEmpty()) {
            index = 0
            resumeInfo = resumeInfoList.value!![0]
        } else {
            index = 0
        }
    }
}