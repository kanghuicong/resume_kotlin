package com.kang.resume.resume

import android.view.View
import androidx.fragment.app.Fragment
import com.kang.resume.R
import com.kang.resume.base.BaseFragment
import com.kang.resume.databinding.ResumeFragmentBinding
import com.kang.resume.databinding.TemplateFragmentBinding

/**
 * 类描述：
 * author:kanghuicong
 */
class ResumeInfoFragment : BaseFragment<ResumeFragmentBinding>() {

    override fun initLayout(): Int {
        return R.layout.resume_fragment
    }

    override fun initViewCreated(view: View) {
    }

}