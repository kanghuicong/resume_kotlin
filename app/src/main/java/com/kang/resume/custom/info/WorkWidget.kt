package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.base.ValueConfig
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.databinding.WidgetPositionBinding
import com.kang.resume.databinding.WidgetWorkBinding
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation

/**
 * 类描述：
 * author:kanghuicong
 */
class WorkWidget(
    context: Context,
    var isLast: Boolean = false,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<WorkExperienceBean> {

    var vb: WidgetWorkBinding = DataBindingUtil.inflate<WidgetWorkBinding>(
        LayoutInflater.from(context),
        R.layout.widget_work,
        this,
        true
    )

    override fun setData(data: WorkExperienceBean?) {
        if (data != null) {
            this.visibility = View.VISIBLE
            //公司
            if (data.company != "") {
                vb.llCompany.visibility = View.VISIBLE
                vb.tvCompany.text = data.company
            } else {
                vb.llCompany.visibility = View.GONE
            }

            //职位
            if (data.position != "") {
                vb.llPosition.visibility = View.VISIBLE
                vb.tvPosition.text = data.position
            } else {
                vb.llPosition.visibility = View.GONE
            }

            //时间
            if (data.startTime != "" && data.endTime != "") {
                vb.llTime.visibility = VISIBLE
                vb.tvTime.text = data.startTime + ValueConfig.space + data.endTime
            } else {
                vb.llTime.visibility = GONE
            }

            //工作内容
            if (data.workContent != "") {
                vb.tvDetail.visibility = VISIBLE
                vb.tvDetail.text = data.workContent
            } else {
                vb.tvDetail.visibility = GONE
            }


            if (isLast) {
                vb.line.visibility = GONE
            } else vb.line.visibility = VISIBLE


            this.setOnClickListener {
                RouterNavigation.doIntentActivity(
                    RouterConfig.WorkRouter,
                    null,
                    data
                )
            }
        } else {
            this.visibility = GONE
        }
    }

    override fun getView(): View {
        return this
    }

}