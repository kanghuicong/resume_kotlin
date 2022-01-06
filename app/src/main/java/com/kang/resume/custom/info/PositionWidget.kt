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
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation

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

    override fun setData(data: JobIntentionBean?) {
        if (data != null) {
            this.visibility = View.VISIBLE

            //职位
            if (data.position != "") {
                vb.tvPosition.visibility = View.VISIBLE

                vb.tvPosition.text = data.position
            } else {
                vb.tvPosition.visibility = View.GONE
            }

            //期望薪资
            if (data.salary != "") {
                vb.tvSalary.visibility = View.VISIBLE
                vb.tvSalary.text = data.salary
            } else {
                vb.tvSalary.visibility = View.GONE
            }

            //期望城市
            if (data.city != "") {
                vb.tvCity.visibility = View.VISIBLE
                vb.tvCity.text = data.city
            } else {
                vb.tvCity.visibility = View.GONE
            }

            //到岗时间
            if (data.entryTime != "") {
                vb.tvTime.visibility = View.VISIBLE
                vb.tvTime.text = data.entryTime
            } else {
                vb.tvTime.visibility = View.GONE
            }
        } else {
            this.visibility = View.GONE
        }

        this.setOnClickListener{
            RouterNavigation.doIntentActivity(
                RouterConfig.JobRouter,
                null,
                data
            )
        }
    }

    override fun getView(): View {
        return  this
    }
}