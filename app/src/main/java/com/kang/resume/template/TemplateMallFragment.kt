package com.kang.resume.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kang.resume.R
import com.kang.resume.adapter.TemplateListAdapter
import com.kang.resume.base.BaseFragment
import com.kang.resume.config.ValueConfig
import com.kang.resume.databinding.TemplateFragmentBinding

/**
 * 类描述：
 * author:kanghuicong
 */

class TemplateMallFragment : BaseFragment<TemplateFragmentBinding>() {

    override fun initLayout(): Int {
        return R.layout.template_fragment
    }

    override fun initViewCreated(view: View) {

        for (index in ValueConfig.templateList.indices) {
            ValueConfig.templateList[index].resId = resources.getIdentifier(
                ValueConfig.templateList[index].cover,
                "drawable",
                activity.packageName
            )
        }

        var layout = GridLayoutManager(activity, 2)
//        var layout = LinearLayoutManager(activity)

        mBinding.rvTemplate.layoutManager = layout

        val adapter =
            TemplateListAdapter(activity, ValueConfig.templateList, R.layout.item_template)
        mBinding.rvTemplate.adapter = adapter

    }


}