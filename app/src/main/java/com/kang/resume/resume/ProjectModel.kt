package com.kang.resume.resume

import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ProjectBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.resume.base.BaseResumeViewModel
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
class ProjectModel (resumeInfoBean: ResumeInfoBean?, projectBean: ProjectBean?) : BaseResumeViewModel() {

    var projectBean = ProjectBean(null, null)

    //开始时间 弹窗默认数据
    var startTimeDefaultDate: Calendar = Calendar.getInstance()
    var endTimeDefaultDate: Calendar = Calendar.getInstance()

    init {
        //赋值操作
        //....
        if (projectBean != null) {
            needDelete.value = true
            this.projectBean = projectBean
        } else {
            needDelete.value = false
            this.projectBean.resumeId = resumeInfoBean?.resumeId
        }

        startTimeDefaultDate = switchTime(startTimeDefaultDate,  this.projectBean.startTime)
        endTimeDefaultDate = switchTime(endTimeDefaultDate,  this.projectBean.endTime)

    }
}