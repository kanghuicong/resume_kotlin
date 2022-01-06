package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.base.ValueConfig
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.databinding.WidgetBaseInfoBinding
import com.kang.resume.databinding.WidgetEducationBinding
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation

/**
 * 类描述：
 * author:kanghuicong
 */
class EducationWidget(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<EducationBean> {

    var vb: WidgetEducationBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.widget_education,
        this,
        true
    )


    override fun setData(data: EducationBean?) {
        if (data != null) {
            this.visibility = VISIBLE

            //学校
            if (data.school != "") {
                vb.tvSchool.visibility = VISIBLE
                vb.tvSchool.text = data.school
            } else {
                vb.tvSchool.visibility = GONE
            }

            //专业
            if (data.major != "") {
                vb.tvMajor.visibility = VISIBLE
                vb.tvMajor.text = data.major
            } else {
                vb.tvMajor.visibility = GONE
            }

            //时间
            if (data.startTime != "" && data.endTime != "") {
                vb.tvTime.visibility = VISIBLE
                vb.tvTime.text = data.startTime + ValueConfig.space + data.endTime
            } else {
                vb.tvTime.visibility = GONE
            }

            //经验
            if (data.experience != "") {
                vb.tvDetail.visibility = VISIBLE
                vb.tvDetail.text = data.experience
            } else {
                vb.tvDetail.visibility = GONE
            }
        } else {
            this.visibility = GONE
        }

        this.setOnClickListener {
            RouterNavigation.doIntentActivity(
                RouterConfig.EducationRouter,
                null,
                data
            )
        }
    }

    override fun getView(): View {
        return this
    }

}