package com.kang.resume.resume

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.elvishew.xlog.XLog
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.base.ValuableConfig
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.custom.info.InfoTitleView
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.ApiService
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.LocalDataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 类描述：
 * author:kanghuicong
 */
class ResumeInfoModel : BaseViewModel() {

    var resumeInfoList = EventMutableLiveData<List<ResumeInfoBean>>()
    var index = 0
    var resumeInfo = EventMutableLiveData<ResumeInfoBean>()

    init {
        resumeInfoList.value = ArrayList()

        if (ValuableConfig.resumeInfoList == null) {
            queryResumeInfoList()
        } else {
            resumeInfoList.value = ValuableConfig.resumeInfoList
            initResume()
        }
    }

    //查询简历
    fun queryResumeInfoList() {
        viewModelScope.launch() {
            if (LocalDataUtils.geIsLogin())
                launch(object : IViewModel<List<ResumeInfoBean>> {
                    override suspend fun launch(): ApiResponse<List<ResumeInfoBean>> {
                        return HttpRequest.api().queryResumeInfoList()
                    }
                }, (object : IHttp<List<ResumeInfoBean>> {
                    override suspend fun success(data: List<ResumeInfoBean>?) {
                        withContext(Dispatchers.Main) {
                            ValuableConfig.resumeInfoList = data
//                            resumeInfoList.postValue(data)

                            resumeInfoList.value = data
                            initResume()
                        }
                    }

                    override suspend fun failure(response: ApiResponse<List<ResumeInfoBean>>) {}
                }))
        }
    }

    private fun initResume() {
        if (resumeInfoList.value != null && resumeInfoList.value!!.isNotEmpty()) {
            if (index > resumeInfoList.value!!.size) {
                index = 0
            }
            resumeInfo.value = resumeInfoList.value!![index]
        }
    }

    //新增一份简历
    fun createResume() {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().saveOrUpdateResume()
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                queryResumeInfoList()
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }

    //删除简历
    fun deleteResume() {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().delResume(resumeInfoList.value!![index].resumeId!!)
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                queryResumeInfoList()
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }

    fun doRouter(router: String) {
        //该简历未创建成功
        doRouter(router,null)
    }


    fun doRouter(router: String, from: String?) {
        //该简历未创建成功
        if (resumeInfo.value?.resumeId == null) {
            queryResumeInfoList()
        } else {
            if (from == null) {
                RouterNavigation.doIntentActivity(
                    router,
                    resumeInfo.value!!
                )
            } else {
                RouterNavigation.doIntentActivity(
                    router,
                    from,
                    resumeInfo.value!!
                )
            }

        }
    }

    //卡片数据
    fun <T> initData(
        infoTitleView: InfoTitleView,
        iWidget: IWidget<T>,
        data: T?
    ) {
        iWidget.setData(data)
        infoTitleView.setChildView(iWidget.getView())
    }

}