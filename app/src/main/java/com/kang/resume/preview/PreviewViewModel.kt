package com.kang.resume.preview

import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.ValuableConfig
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel

/**
 * 类描述：
 * author:kanghuicong
 */
class PreviewViewModel : BaseViewModel() {

    fun export(resumeId: Int,iExport: IExport) {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().export(resumeId)
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                iExport.success()
            }

            override suspend fun failure(response: ApiResponse<Any>) {
            }
        }))
    }

    interface IExport {
        fun success()

    }
}