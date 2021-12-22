package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.databinding.WidgetBaseInfoBinding
import com.kang.resume.databinding.WidgetPositionBinding
import com.kang.resume.pro.IWidget

/**
 * 类描述：
 * author:kanghuicong
 */
class PositionWidget(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<JobIntentionBean> {

    var vb: WidgetPositionBinding = DataBindingUtil.inflate<WidgetPositionBinding>(
        LayoutInflater.from(context),
        R.layout.widget_position,
        this,
        true
    )

    override fun setData(jobIntentionBean: JobIntentionBean?) {
        if (jobIntentionBean != null) {
            this.visibility = View.VISIBLE

            //职位
            if (jobIntentionBean.position != "") {
                vb.tvPosition.visibility = View.VISIBLE

                vb.tvPosition.text = jobIntentionBean.position
            } else {
                vb.tvPosition.visibility = View.GONE
            }

            //期望薪资
            if (jobIntentionBean.salary != "") {
                vb.tvSalary.visibility = View.VISIBLE
                vb.tvSalary.text = jobIntentionBean.salary
            } else {
                vb.tvSalary.visibility = View.GONE
            }

            //期望城市
            if (jobIntentionBean.city != "") {
                vb.tvCity.visibility = View.VISIBLE
                vb.tvCity.text = jobIntentionBean.city
            } else {
                vb.tvCity.visibility = View.GONE
            }

            //到岗时间
            if (jobIntentionBean.entryTime != "") {
                vb.tvTime.visibility = View.VISIBLE
                vb.tvTime.text = jobIntentionBean.entryTime
            } else {
                vb.tvTime.visibility = View.GONE
            }
        } else {
            this.visibility = View.GONE
        }
    }

    override fun getView(): View {
        return  this
    }
}