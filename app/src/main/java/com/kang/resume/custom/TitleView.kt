package com.kang.resume.custom

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.kang.resume.R
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.ViewTitleBinding

/**
 * 类描述：
 * author:kanghuicong
 */
class TitleView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {


    private var iToolClick: IToolClick? = null
    private val vb = DataBindingUtil.inflate<ViewTitleBinding>(
        LayoutInflater.from(context),
        R.layout.view_title,
        this,
        true
    )

    init {

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TitleView
        )

        if (typedArray.getBoolean(R.styleable.TitleView_needBack, true)) {
            vb.ivBack.visibility = View.VISIBLE
            vb.ivBack.setOnClickListener {
                (context as Activity).finish()
            }
        } else {
            vb.ivBack.visibility = View.GONE
        }

        vb.titleTxt.text = typedArray.getString(R.styleable.TitleView_title)
        vb.titleTxt.setTextColor(
            typedArray.getColor(
                R.styleable.TitleView_title_color,
                0xFF4A4A4A.toInt()
            )
        )

        vb.titleTool.text = (typedArray.getString(R.styleable.TitleView_title_right))
        vb.titleTool.setTextColor(
            typedArray.getColor(
                R.styleable.TitleView_title_right_color,
                0xFF4A4A4A.toInt()
            )
        )

        typedArray.recycle()

        if (iToolClick != null) {
            vb.titleTool.setOnClickListener { iToolClick!!.click() }
        }
    }

    fun setTitle(title: String) :TitleView{
        vb.titleTxt.text = title
        return this
    }

    fun setToolClick(iToolClick: IToolClick) {
        this.iToolClick = iToolClick
    }

    interface IToolClick {
        fun click()
    }
}

