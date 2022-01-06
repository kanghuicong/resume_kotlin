package com.kang.resume.resume

import com.kang.resume.base.ValueConfig
import com.kang.resume.bean.CertificateBean
import com.kang.resume.bean.HobbyBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.resume.base.BaseResumeViewModel
import com.kang.resume.router.RouterConfig

/**
 * 类描述：
 * author:kanghuicong
 */
class TagModel(from: String?, resumeInfoBean: ResumeInfoBean?, data: Any?) :
    BaseResumeViewModel() {

    var certificateBean = CertificateBean(null, null)
    var hobbyBean = HobbyBean(null, null)

    init {
        if (data != null) {
            needDelete.value = true
            if (from == RouterConfig.CertificateFrom) {
                this.certificateBean = data as CertificateBean
            } else if (from == RouterConfig.HobbyFrom) {
                this.hobbyBean = data as HobbyBean
            }
        } else {
            needDelete.value = false
            if (from == RouterConfig.CertificateFrom) {
                this.certificateBean.resumeId = resumeInfoBean?.resumeId
            } else if (from == RouterConfig.HobbyFrom) {
                this.hobbyBean.resumeId = resumeInfoBean?.resumeId
            }

        }
    }

}