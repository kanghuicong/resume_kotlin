package com.kang.resume.resume

import com.kang.resume.base.BaseViewModel
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.resume.base.BaseResumeViewModel
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
class WorkModel(resumeInfoBean: ResumeInfoBean?, workExperienceBean: WorkExperienceBean?) :
    BaseResumeViewModel() {

    var workExperienceBean = WorkExperienceBean(null, null)
    //开始时间 弹窗默认数据
    var startTimeDefaultDate: Calendar = Calendar.getInstance()
    var endTimeDefaultDate: Calendar = Calendar.getInstance()

    init {
        if (workExperienceBean != null) {
            needDelete.value = true
            this.workExperienceBean = workExperienceBean
        } else {
            needDelete.value = false
            this.workExperienceBean.resumeId = resumeInfoBean?.resumeId
        }

        startTimeDefaultDate = switchTime(startTimeDefaultDate,  this.workExperienceBean.startTime)
        endTimeDefaultDate = switchTime(endTimeDefaultDate,  this.workExperienceBean.endTime)

    }
}