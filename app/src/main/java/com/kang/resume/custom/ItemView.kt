package com.kang.resume.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.databinding.ViewItemBinding
import com.kang.resume.databinding.ViewTitleBinding

/**
 * 类描述：
 * author:kanghuicong
 */

class ItemView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var iItemClick: IItemClick? = null

    init {
        val vb = DataBindingUtil.inflate<ViewItemBinding>(
            LayoutInflater.from(context),
            R.layout.view_item,
            this,
            true
        )

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ItemView
        )

        val img = typedArray.getDrawable(R.styleable.ItemView_item_img)
        if (img != null) {
            vb.itemImg.setImageDrawable(img)
        }

        val title = typedArray.getString(R.styleable.ItemView_item_title)
        if (title != null) {
            vb.itemTitle.text = title
        }

        typedArray.recycle()

        this.setOnClickListener {
            if (iItemClick != null) iItemClick?.click()
        }

    }

    fun setItemClick(iItemClick: IItemClick) {
        this.iItemClick = iItemClick
    }

    interface IItemClick {
        fun click()
    }

}
