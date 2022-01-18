package com.kang.resume.resume

import com.kang.resume.base.BaseViewModel
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.resume.base.BaseResumeViewModel
import com.kang.resume.router.RouterConfig

/**
 * 类描述：
 * author:kanghuicong
 */
class WriteContentModel(from: String?, resumeInfoBean: ResumeInfoBean?, content: String?) :
    BaseResumeViewModel() {

    var resumeId: Int? = null
    var from: String? = null
    var content: String? = null

    init {
        this.from = from
        this.content = content ?: ""

        if (resumeInfoBean != null) {
            resumeId = resumeInfoBean?.resumeId!!
        }

        when (from) {
            RouterConfig.SelfEvaluationFrom -> {
                needDelete.value = !content.isNullOrEmpty()
            }
            else -> {
                needDelete.value = false
            }
        }
    }
}