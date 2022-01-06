package com.kang.resume.resume

import com.kang.resume.R
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.SkillBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.resume.base.BaseResumeViewModel

/**
 * 类描述：
 * author:kanghuicong
 */
class SkillsModel (resumeInfoBean: ResumeInfoBean?, skillBean: SkillBean?) :
    BaseResumeViewModel() {

    var proficiencyIndex = EventMutableLiveData<Int>()

    var skillBean = SkillBean(null, null)


    init {
        if (skillBean!= null) {
            needDelete.value = true
            this.skillBean = skillBean
        } else {
            needDelete.value = false
            this.skillBean.resumeId = resumeInfoBean?.resumeId
        }

        //到岗时间
        when (this.skillBean.proficiency) {
            getString(R.string.proficiency_generally) -> proficiencyIndex.value = 0
            getString(R.string.proficiency_good) -> proficiencyIndex.value = 1
            getString(R.string.proficiency_skilled) -> proficiencyIndex.value = 2
            getString(R.string.proficiency_proficient) -> proficiencyIndex.value = 3
            else -> proficiencyIndex.value = -1
        }
    }
}