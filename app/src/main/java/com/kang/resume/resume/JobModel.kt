package com.kang.resume.resume

import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.resume.base.BaseResumeViewModel

/**
 * 类描述：
 * author:kanghuicong
 */
class JobModel(resumeInfoBean: ResumeInfoBean?) : BaseResumeViewModel() {

    var timeIndex = EventMutableLiveData<Int>()

    var jobIntentionBean = JobIntentionBean(null, null)

    init {
        if (resumeInfoBean?.jobIntention != null) {
            needDelete.value = true
            jobIntentionBean = resumeInfoBean.jobIntention!!
        } else {
            needDelete.value = false
            jobIntentionBean.resumeId = resumeInfoBean?.resumeId
        }

        //到岗时间
        when (jobIntentionBean.entryTime) {
            getString(R.string.job_time_anytime) -> timeIndex.value = 0
            getString(R.string.job_time_7_days) -> timeIndex.value = 1
            getString(R.string.job_time_1_month) -> timeIndex.value = 2
            getString(R.string.job_time_3_month) -> timeIndex.value = 3
            getString(R.string.job_time_no) -> timeIndex.value = 4
            else -> timeIndex.value = -1
        }
    }
}