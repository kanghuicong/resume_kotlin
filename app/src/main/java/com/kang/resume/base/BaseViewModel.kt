package com.kang.resume.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kang.resume.MainApplication
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.BaseResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */
open class BaseViewModel : ViewModel() {
    val loadingLiveData = EventMutableLiveData<Boolean>()
    val toastLiveData = EventMutableLiveData<Any>()
    val finishLiveData = EventMutableLiveData<Boolean>()
    val loginLiveData = EventMutableLiveData<BaseResponse>()

    fun <T> launch(
        iViewModel: IViewModel<T>,
        iHttp: IHttp<T>?,
        needStart: Boolean = true,
        needEnd: Boolean = true
    ) {
        if (needStart) loadingLiveData.postValue(true)

        viewModelScope.launch(Dispatchers.Main) {
            HttpRequest.doHttp(
                {
                    iViewModel.launch()
                },
                object : IHttp<T> {
                    override suspend fun success(data: T?) {
                        if (needEnd) {
                            loadingLiveData.postValue(false)
                        }
                        iHttp?.success(data)
                    }

                    override suspend fun failure(response: ApiResponse<T>) {
                        loadingLiveData.postValue(false)
                        loginLiveData.postValue(BaseResponse(response.code, response.message))

                        iHttp?.failure(response)
                    }
                }
            )
        }
    }


    fun <T> launchWithNoLoad(
        iViewModel: IViewModel<T>,
        iHttp: IHttp<T>?
    ) {
        launch(iViewModel, iHttp, needStart = false, needEnd = false)
    }

    fun <T> launchWithEndLoad(
        iViewModel: IViewModel<T>,
        iHttp: IHttp<T>?
    ) {
        launch(iViewModel, iHttp, needStart = false, needEnd = true)
    }

    fun <T> launchWithStartLoad(
        iViewModel: IViewModel<T>,
        iHttp: IHttp<T>?
    ) {
        launch(iViewModel, iHttp, needStart = true, needEnd = false)
    }

    fun getString(resId: Int): String {
        return MainApplication.getApplication().getString(resId)
    }
}

