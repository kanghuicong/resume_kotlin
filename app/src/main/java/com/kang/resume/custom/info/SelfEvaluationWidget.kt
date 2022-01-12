package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.bean.ProjectBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.databinding.WidgetProjectBinding
import com.kang.resume.databinding.WidgetSelfEvaluationBinding
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation

/**
 * 类描述：
 * author:kanghuicong
 */
class SelfEvaluationWidget(
    context: Context,
    var resumeInfoBean: ResumeInfoBean? = null,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<String> {

    var vb: WidgetSelfEvaluationBinding = DataBindingUtil.inflate<WidgetSelfEvaluationBinding>(
        LayoutInflater.from(context),
        R.layout.widget_self_evaluation,
        this,
        true
    )

    override fun setData(data: String?) {
        if (!data.isNullOrEmpty()) {
            this.visibility = View.VISIBLE

            //链接
            if (!data.isNullOrEmpty()) {
                vb.tvEvaluation.visibility = VISIBLE
                vb.tvEvaluation.text = data
            } else {
                vb.tvEvaluation.visibility = GONE
            }

            this.setOnClickListener {
                RouterNavigation.doIntentActivity(
                    RouterConfig.WriteRouter,
                    RouterConfig.SelfEvaluationFrom,
                    resumeInfoBean,
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