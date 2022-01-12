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
import com.kang.resume.resume.base.BaseResumeViewModel
import java.lang.Exception
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
class BaseInfoModel(resumeInfoBean: ResumeInfoBean?,baseInfo: BaseInfoBean?) : BaseResumeViewModel() {

    //性别 弹窗下标
    var genderIndex = EventMutableLiveData<Int>()

    var marryIndex = EventMutableLiveData<Int>()

    var politicalIndex = EventMutableLiveData<Int>()

    //出生年月 弹窗默认数据
    var birthdayDefaultDate: Calendar = Calendar.getInstance()

    //参加工作时间 弹窗默认数据
    var startWorkTimeDefaultDate: Calendar = Calendar.getInstance()

    var recordIndex = EventMutableLiveData<Int>()

    var baseInfoBean = BaseInfoBean(null, null)

    init {
        //赋值操作
        //....
        if (baseInfo != null) {
            needDelete.value = true
            baseInfoBean = baseInfo
        }else{
            needDelete.value = false
            baseInfoBean.resumeId = resumeInfoBean?.resumeId
        }

        //数据处理
        when (baseInfoBean.gender) {
            getString(R.string.gender_man) -> genderIndex.value = 0
            getString(R.string.gender_women) -> genderIndex.value = 1
            else -> genderIndex.value = -1
        }

        //数据处理
        when (this.baseInfoBean.record) {
            getString(R.string.junior_high_school) -> recordIndex.value = 0
            getString(R.string.high_school) -> recordIndex.value = 1
            getString(R.string.technical_secondary_school) -> recordIndex.value = 2
            getString(R.string.junior_college) -> recordIndex.value = 3
            getString(R.string.undergraduate) -> recordIndex.value = 4
            getString(R.string.master) -> recordIndex.value = 5
            getString(R.string.doctor) -> recordIndex.value = 6
            getString(R.string.postdoc) -> recordIndex.value = 7
            getString(R.string.other) -> recordIndex.value = 8
            else -> recordIndex.value = -1
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

}