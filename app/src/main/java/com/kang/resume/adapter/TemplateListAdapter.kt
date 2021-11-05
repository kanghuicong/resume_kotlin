package com.kang.resume.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.kang.resume.R
import com.kang.resume.base.BaseAdapter
import com.kang.resume.base.RecyclerHolder
import com.kang.resume.bean.TemplateBean

/**
 * 类描述：
 * author:kanghuicong
 */

class TemplateListAdapter(
    private var context: Context,
    private var list: List<TemplateBean>, layoutId: Int
) : BaseAdapter<TemplateBean, ViewDataBinding>(context, list, layoutId) {

    override fun onBind(holder: RecyclerHolder<ViewDataBinding>, position: Int) {
//        val ivCover = holder.itemView.findViewById<View>(R.id.iv_template_cover)
//        val layoutParams = ivCover.layoutParams
//        layoutParams.height = layoutParams.width * 248 / 175
//        ivCover.layoutParams = layoutParams

        holder.itemView.findViewById<ImageView>(R.id.iv_template_cover).setOnClickListener {
            Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
        }
    }

}