package com.kang.resume.resume

import com.kang.resume.R
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.resume.base.BaseResumeViewModel
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
class EducationModel(resumeInfoBean: ResumeInfoBean?, educationBean: EducationBean?) :
    BaseResumeViewModel() {

    var educationBean = EducationBean(null, null)

    var recordIndex = EventMutableLiveData<Int>()

    //开始时间 弹窗默认数据
    var startTimeDefaultDate: Calendar = Calendar.getInstance()
    var endTimeDefaultDate: Calendar = Calendar.getInstance()


    init {
        //赋值操作
        //....
        if (educationBean != null) {
            needDelete.value = true
            this.educationBean = educationBean
        } else {
            needDelete.value = false
            this.educationBean.resumeId = resumeInfoBean?.resumeId
        }

        //数据处理
        when (this.educationBean.record) {
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

        startTimeDefaultDate = switchTime(startTimeDefaultDate,  this.educationBean.startTime)
        endTimeDefaultDate = switchTime(endTimeDefaultDate,  this.educationBean.endTime)

    }

}