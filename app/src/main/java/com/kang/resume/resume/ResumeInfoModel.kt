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

    var resumeInfoList: ArrayList<ResumeInfoBean>? = arrayListOf()
    var haseResume = EventMutableLiveData<Boolean>()
    var index = 0
    var resumeInfo = EventMutableLiveData<ResumeInfoBean>()

    init {

        if (ValuableConfig.resumeInfoList == null) {
            haseResume.value = false
            queryResumeInfoList(false)

        } else {
            resumeInfoList = ValuableConfig.resumeInfoList!!
            haseResume.value = true
            initResume(false)
        }
    }

    //查询简历
    fun queryResumeInfoList(isLast: Boolean) {
        viewModelScope.launch() {
            if (LocalDataUtils.geIsLogin())
                launch(object : IViewModel<List<ResumeInfoBean>> {
                    override suspend fun launch(): ApiResponse<List<ResumeInfoBean>> {
                        return HttpRequest.api().queryResumeInfoList()
                    }
                }, (object : IHttp<List<ResumeInfoBean>> {
                    override suspend fun success(data: List<ResumeInfoBean>?) {
                        withContext(Dispatchers.Main) {
                            ValuableConfig.resumeInfoList = data as ArrayList<ResumeInfoBean>?

                            if (isLast) index = data?.size!! - 1
                            for (position in data?.indices!!) {
                                data[position].isClick = index == position
                            }
                            resumeInfoList = data
                            initResume(isLast)
                        }
                    }

                    override suspend fun failure(response: ApiResponse<List<ResumeInfoBean>>) {}
                }))
        }
    }

    private fun initResume(isLast: Boolean) {
        if (resumeInfoList != null && resumeInfoList!!.isNotEmpty()) {

            haseResume.value = true

            if (index >= resumeInfoList!!.size) {
                index = 0
            }
            resumeInfo.value = resumeInfoList!![index]
        } else haseResume.value = false

    }

    //切换简历
    fun switchTag(position: Int) {
        if (index == position) return
        index = position
        for (position in resumeInfoList?.indices!!) {
            resumeInfoList!![position].isClick = index == position
        }
        initResume(false)
    }

    //新增一份简历
    fun createResume() {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().saveOrUpdateResume()
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                queryResumeInfoList(true)
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }

    //删除简历
    fun deleteResume() {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().delResume(resumeInfoList!![index].resumeId!!)
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                index = 0
                queryResumeInfoList(false)
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }

    fun doRouter(router: String) {
        //该简历未创建成功
        doRouter(router, null)
    }


    fun doRouter(router: String, from: String?) {
        //该简历未创建成功
        if (resumeInfo.value?.resumeId == null) {
            queryResumeInfoList(false)
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