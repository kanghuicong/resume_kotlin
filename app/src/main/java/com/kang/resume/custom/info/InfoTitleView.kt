package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.databinding.ViewInfoTitileBinding
import com.kang.resume.databinding.ViewTitleBinding
import com.kang.resume.pro.IClick

/**
 * 类描述：
 * author:kanghuicong
 */
class InfoTitleView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var data: Any? = null
    var iClick: IClick? = null

    var vb: ViewInfoTitileBinding = DataBindingUtil.inflate<ViewInfoTitileBinding>(
        LayoutInflater.from(context),
        R.layout.view_info_titile,
        this,
        true
    )

    init {

        vb.llAdd.setOnClickListener {
            if (iClick != null) {
                iClick!!.click(it)
            }
        }

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.InfoTitleView
        )

        vb.tvInfoTitle.text = typedArray.getString(R.styleable.InfoTitleView_info_title)

        vb.llChildView.visibility = GONE
        typedArray.recycle()

    }

    fun setChildView(view: View): InfoTitleView {
        vb.llChildView.removeAllViews()
        vb.llChildView.visibility = VISIBLE
        vb.llChildView.addView(view)
        return this
    }

    fun showAddImg(isShow: Boolean): InfoTitleView {
        if (isShow) {
            vb.ivAdd.visibility = VISIBLE
            vb.llAdd.setOnClickListener {
                if (iClick != null) {
                    iClick!!.click(it)
                }
            }
        } else {
            vb.ivAdd.visibility = INVISIBLE
            vb.llAdd.setOnClickListener { }
        }
        return this
    }

    fun setChildView(views: List<View>): InfoTitleView {
        vb.llChildView.removeAllViews()
        vb.llChildView.visibility = VISIBLE
        for (view: View in views)
            vb.llChildView.addView(view)
        return this
    }
}