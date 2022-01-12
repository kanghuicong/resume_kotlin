package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.base.ValueConfig
import com.kang.resume.bean.ProjectBean
import com.kang.resume.databinding.WidgetProjectBinding
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation

/**
 * 类描述：
 * author:kanghuicong
 */
class ProjectWidget(
    context: Context,
    var isLast: Boolean = false,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<ProjectBean> {

    var vb: WidgetProjectBinding = DataBindingUtil.inflate<WidgetProjectBinding>(
        LayoutInflater.from(context),
        R.layout.widget_project,
        this,
        true
    )

    override fun setData(data: ProjectBean?) {
        if (data != null) {
            this.visibility = View.VISIBLE

            //项目名称
            if (data.projectName != "") {
                vb.llProject.visibility = View.VISIBLE
                vb.tvProject.text = data.projectName
            } else {
                vb.llProject.visibility = View.GONE
            }
            //公司
            if (data.company != "") {
                vb.llCompany.visibility = View.VISIBLE
                vb.tvCompany.text = data.company
            } else {
                vb.llCompany.visibility = View.GONE
            }

            //时间
            if (data.startTime != "" && data.endTime != "") {
                vb.llTime.visibility = VISIBLE
                vb.tvTime.text = data.startTime + ValueConfig.space + data.endTime
            } else {
                vb.llTime.visibility = GONE
            }

            //项目描述
            if (data.description != "") {
                vb.tvDetail.visibility = VISIBLE
                vb.tvDetail.text = data.description
            } else {
                vb.tvDetail.visibility = GONE
            }

            //链接
            if (data.url != "") {
                vb.llUrl.visibility = VISIBLE
                vb.tvUrl.text = data.url
            } else {
                vb.llUrl.visibility = GONE
            }

            if (isLast) {
                vb.line.visibility = GONE
            } else vb.line.visibility = VISIBLE


            this.setOnClickListener {
                RouterNavigation.doIntentActivity(
                    RouterConfig.ProjectRouter,
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