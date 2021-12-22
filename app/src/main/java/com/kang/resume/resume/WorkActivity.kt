package com.kang.resume.resume

import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.databinding.ResumeWorkActivityBinding

/**
 * 类描述：
 * author:kanghuicong
 */
class WorkActivity: BaseActivity<ResumeWorkActivityBinding, WorkModel>() {
    override fun initLayout(): Int {
        return R.layout.resume_work_activity
    }

    override fun initViewModel(): WorkModel {
        TODO("Not yet implemented")
    }

    override fun ResumeWorkActivityBinding.initBinding() {
        TODO("Not yet implemented")
    }
}