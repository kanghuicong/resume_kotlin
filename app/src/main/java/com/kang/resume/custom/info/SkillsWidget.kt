package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.base.ValueConfig
import com.kang.resume.bean.SkillBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.databinding.WidgetSkillBinding
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.vondear.rxtool.RxDeviceTool

/**
 * 类描述：
 * author:kanghuicong
 */
class SkillsWidget(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<SkillBean> {

    var vb: WidgetSkillBinding = DataBindingUtil.inflate<WidgetSkillBinding>(
        LayoutInflater.from(context),
        R.layout.widget_skill,
        this,
        true
    )

    override fun setData(data: SkillBean?) {
        if (data != null) {
            this.visibility = View.VISIBLE

            //项目名称
            if (data.skillName != "") {
                vb.tvSkill.visibility = View.VISIBLE
                vb.tvSkill.text = data.skillName
            } else {
                vb.tvSkill.visibility = View.GONE
            }

            vb.bg.width = RxDeviceTool.getScreenWidth(context) / 3
            val proficiency = (data.proficiencyValue.toFloat() / 100)
            vb.bgProficiency.width =
                (RxDeviceTool.getScreenWidth(context) / 3 * proficiency).toInt()

            this.setOnClickListener {
                RouterNavigation.doIntentActivity(
                    RouterConfig.SkillsRouter,
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