package com.kang.resume.custom.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.bean.CertificateBean
import com.kang.resume.bean.HobbyBean
import com.kang.resume.databinding.WidgetTagBinding
import com.kang.resume.preview.custom.RadiusCardView
import com.kang.resume.preview.utils.Config
import com.kang.resume.pro.IClick
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * 类描述：
 * author:kanghuicong
 */
class TagWidget(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), IWidget<Any> {

    var vb: WidgetTagBinding = DataBindingUtil.inflate<WidgetTagBinding>(
        LayoutInflater.from(context),
        R.layout.widget_tag,
        this,
        true
    )

    override fun setData(data: Any?) {
        if (data is List<*>) {
            if (data.size == 0) this.visibility = View.GONE
            else {
                this.visibility = View.VISIBLE
                val tagsAdapter = TagsAdapter(data as List<Any>)
                vb.llTag.adapter = tagsAdapter
            }
        } else {
            this.visibility = View.GONE
        }
    }

    override fun getView(): View {
        return this
    }

}

open class TagsAdapter(list: List<Any>) : TagAdapter<Any>(list) {

    private lateinit var cardView: RadiusCardView
    private lateinit var tvTag: TextView
    var iTagsClick: ITagsClick? = null
    override fun getView(parent: FlowLayout?, position: Int, tag: Any?): View {
        val v = LayoutInflater.from(parent!!.context).inflate(
            R.layout.item_widget_tag,
            parent,
            false
        )

        cardView = v.findViewById(R.id.card)
        tvTag = v.findViewById(R.id.tv_tag)

        cardView.setAllRadius(10f)

        var tagText = ""
        var from = ""

        when (tag) {
            is CertificateBean -> {
                tagText = tag.certificate
                from = RouterConfig.CertificateFrom
            }
            is HobbyBean -> {
                tagText = tag.hobby
                from = RouterConfig.HobbyFrom
            }
            is String -> {
                tagText = tag
            }
        }

        tvTag.text = tagText
        tvTag.setOnClickListener {
            if (iTagsClick != null) iTagsClick!!.click(it, tag)
            else {
                if (tag !is String) {
                    RouterNavigation.doIntentActivity(
                        RouterConfig.TagRouter,
                        from,
                        null,
                        tag
                    )
                }
            }

        }
        return v
    }


    interface ITagsClick {
        fun click(view: View, str: Any?)
    }
}