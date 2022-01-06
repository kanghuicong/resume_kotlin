package com.kang.resume.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.kang.resume.R
import com.kang.resume.base.BaseAdapter
import com.kang.resume.base.RecyclerHolder
import com.kang.resume.base.ValuableConfig
import com.kang.resume.bean.TemplateCoverListBean
import com.kang.resume.preview.PreViewActivity
import com.kang.resume.preview.bean.TemplateBean
import com.kang.resume.utils.GsonUtils
import com.vondear.rxtool.view.RxToast
import java.io.Serializable

/**
 * 类描述：
 * author:kanghuicong
 */

class TemplateListAdapter(
    private var context: Context,
    private var list: List<TemplateCoverListBean>, layoutId: Int
) : BaseAdapter<TemplateCoverListBean, ViewDataBinding>(context, list, layoutId) {
    var templateList : ArrayList<TemplateBean> = GsonUtils.getGsonList(context)

    override fun onBind(holder: RecyclerHolder<ViewDataBinding>, position: Int) {
//        val ivCover = holder.itemView.findViewById<View>(R.id.iv_template_cover)
//        val layoutParams = ivCover.layoutParams
//        layoutParams.height = layoutParams.width * 248 / 175
//        ivCover.layoutParams = layoutParams

        holder.itemView.findViewById<ImageView>(R.id.iv_template_cover).setOnClickListener {
//            Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()

            if (ValuableConfig.resumeInfoList==null){
                RxToast.normal("请先创建一份简历")
                return@setOnClickListener
            }

            val bundle = Bundle()
            bundle.putSerializable("json",ValuableConfig.resumeInfoList as Serializable)
            bundle.putSerializable("template", templateList[position])

            val intent = Intent(context, PreViewActivity::class.java)

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

}


