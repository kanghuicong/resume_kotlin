package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.databinding.ViewInfoTitileBinding
import com.kang.resume.databinding.WidgetBaseInfoBinding
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.kang.resume.utils.SwitchUtils
import com.kang.resume.utils.VerifyUtils

/**
 * 类描述：
 * author:kanghuicong
 */
class BaseInfoWidget(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<BaseInfoBean> {
    var vb: WidgetBaseInfoBinding = DataBindingUtil.inflate<WidgetBaseInfoBinding>(
        LayoutInflater.from(context),
        R.layout.widget_base_info,
        this,
        true
    )

    override fun setData(data: BaseInfoBean?) {
        if (data != null) {
            this.visibility = View.VISIBLE
            //姓名
            if (data.name != "") {
                vb.tvBaseName.text = data.name
            } else {
                vb.tvBaseName.text = context.getString(R.string.unfilled)
            }

            //年龄
            if (data.birthday != "") {
                vb.llBaseAge.visibility = View.VISIBLE
                vb.tvBaseAge.text = SwitchUtils.switchYear(data.birthday)
            } else {
                vb.llBaseAge.visibility = View.GONE
            }

            //工作经验
            if (data.startWorkTime != "") {
                vb.llBaseWorkTime.visibility = View.VISIBLE
                vb.tvBaseWorkTime.text = SwitchUtils.switchYear(data.startWorkTime)
            } else {
                vb.llBaseWorkTime.visibility = View.GONE
            }

            if (data.email != "") {
                vb.llBaseEmail.visibility = View.VISIBLE
                vb.tvBaseEmail.text = data.email
            } else {
                vb.llBaseWorkTime.visibility = View.GONE
            }

            if (data.phone != "") {
                vb.llBasePhone.visibility = View.VISIBLE
                vb.tvBasePhone.text = data.phone
            } else {
                vb.llBaseWorkTime.visibility = View.GONE
            }
        } else {
            this.visibility = View.GONE
        }

        this.setOnClickListener {
            RouterNavigation.doIntentActivity(
                RouterConfig.BaseInfoRouter,
                null,
                data
            )
        }
    }

    override fun getView(): View {
        return this
    }

}
