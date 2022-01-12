package com.kang.resume.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import com.kang.resume.R
import com.kang.resume.base.BaseAdapter
import com.kang.resume.base.RecyclerHolder
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.TemplateCoverListBean

/**
 * 类描述：
 * author:kanghuicong
 */
class ResumeTagAdapter(
    private var context: Context,
    private var list: List<ResumeInfoBean>?,
    layoutId: Int,
) : BaseAdapter<ResumeInfoBean, ViewDataBinding>(context, list, layoutId) {

    var iResumeClick: IResumeClick? = null
    override fun onBind(holder: RecyclerHolder<ViewDataBinding>, position: Int) {
        val tv = holder.itemView.findViewById<TextView>(R.id.tv_resume_name)
//        val iv = holder.itemView.findViewById<ImageView>(R.id.iv_resume_add)
        val cl = holder.itemView.findViewById<ConstraintLayout>(R.id.cl_resume)

        if (list?.get(position)?.resumeId == -1) {
            tv.text = "添加"

            cl.background = context.resources.getDrawable(R.drawable.bt_main_30)
            tv.setTextColor(context.resources.getColor(R.color.white))

            cl.setOnClickListener {
                iResumeClick?.add()
            }
        } else {
            tv.text = if (list?.get(position)?.resumeName.isNullOrEmpty()) "简历$position"
            else list?.get(position)?.resumeName

            cl.setOnClickListener {
                iResumeClick?.click(position)
            }

            if (list?.get(position)?.isClick!!) {
                cl.background = context.resources.getDrawable(R.drawable.bt_main_30)
                tv.setTextColor(context.resources.getColor(R.color.white))
            } else {
                cl.background = context.resources.getDrawable(R.drawable.bt_grey_30)
                tv.setTextColor(context.resources.getColor(R.color.black94))
            }
        }
    }
}

interface IResumeClick {
    fun click(position: Int)

    fun add()
}