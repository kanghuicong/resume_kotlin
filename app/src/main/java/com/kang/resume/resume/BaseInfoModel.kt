package com.kang.resume.resume

import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.event.UpdateResumeLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import java.lang.Exception
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
class BaseInfoModel(resumeInfoBean: ResumeInfoBean?) : BaseViewModel() {

    //性别 弹窗下标
    var genderIndex = EventMutableLiveData<Int>()

    var marryIndex = EventMutableLiveData<Int>()

    var politicalIndex = EventMutableLiveData<Int>()

    //出生年月 弹窗默认数据
    var birthdayDefaultDate: Calendar = Calendar.getInstance()

    //参加工作时间 弹窗默认数据
    var startWorkTimeDefaultDate: Calendar = Calendar.getInstance()


    var baseInfoBean = BaseInfoBean(null, null)

    init {
        //赋值操作
        //....
        if (resumeInfoBean?.baseInfo != null) {
            baseInfoBean = resumeInfoBean.baseInfo!!
        }

        //数据处理
        when (baseInfoBean.gender) {
            getString(R.string.gender_man) -> genderIndex.value = 0
            getString(R.string.gender_women) -> genderIndex.value = 1
            else -> genderIndex.value = -1
        }

        when (baseInfoBean.marryStatus) {
            getString(R.string.marry_status_unmarried) -> marryIndex.value = 0
            getString(R.string.marry_status_married) -> marryIndex.value = 1
            getString(R.string.marry_status_secret) -> marryIndex.value = 2
            else -> marryIndex.value = -1
        }

        when (baseInfoBean.politicalStatus) {
            getString(R.string.political_status_cpc_member) -> politicalIndex.value = 0
            getString(R.string.political_status_probationary_member) -> politicalIndex.value = 1
            getString(R.string.political_status_youth_member) -> politicalIndex.value = 2
            getString(R.string.political_status_masses) -> politicalIndex.value = 3
            getString(R.string.political_status_democratic_parties) -> politicalIndex.value = 4
            getString(R.string.political_status_others) -> politicalIndex.value = 5
            else -> politicalIndex.value = -1
        }


        birthdayDefaultDate = switchTime(birthdayDefaultDate, baseInfoBean.birthday)
        startWorkTimeDefaultDate = switchTime(startWorkTimeDefaultDate, baseInfoBean.startWorkTime)

    }

    fun keep(baseInfoBean: BaseInfoBean) {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return HttpRequest.api().saveOrUpdateBaseInfo(baseInfoBean)
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                finishLiveData.postValue(true)
                UpdateResumeLiveData.getInstance().postValue(true)
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }


    fun switchDate(date: Date): String {
        return try {
            //2020年12月12日 12时12分12秒
            val dataTime = date.toLocaleString()
            //2020年12月12日
            val ary = dataTime.split(" ")

            ary[0]
        } catch (e: Exception) {
            ""
        }
    }

    fun switchTime(calendar: Calendar, time: String?): Calendar {
        if (time == null || time == "") return calendar
        try {
            var mTime = time
            mTime = mTime.replace("年", "-")
            mTime = mTime.replace("月", "-")
            mTime = mTime.replace("日", "")

            val dateAry = mTime.split("-")

            calendar.set(
                dateAry[0].toInt(),
                dateAry[1].toInt() - 1,
                dateAry[2].toInt()
            )

            return calendar
        } catch (e: Exception) {
            return calendar
        }
    }
}