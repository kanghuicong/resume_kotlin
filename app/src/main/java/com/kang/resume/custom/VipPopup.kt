package com.kang.resume.custom

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kang.resume.R
import com.kang.resume.base.BaseAdapter
import com.kang.resume.base.RecyclerHolder
import com.kang.resume.bean.VipBean
import com.lxj.xpopup.core.CenterPopupView
import com.vondear.rxtool.view.RxToast


/**
 * 类描述：
 * author:kanghuicong
 */
class VipPopup(context: Context, var list: List<VipBean>,var iClick: IClick) : CenterPopupView(context) {
    private lateinit var tvTitle: TextView
    private lateinit var rv: RecyclerView
    private lateinit var btVip: TextView
    private lateinit var adapter: VipAdapter

    // 返回自定义弹窗的布局
    override fun getImplLayoutId(): Int {
        return R.layout.pop_vip
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    override fun onCreate() {
        super.onCreate()

        tvTitle = findViewById(R.id.tv_title)
        rv = findViewById(R.id.rv)
        btVip = findViewById(R.id.bt_vip)

        adapter = VipAdapter(context, list, R.layout.item_vip)
        adapter.setClickListener(object : VipAdapter.IClick {
            override fun click(index: Int, days: String) {
                btVip.text = String.format(
                    context.getString(R.string.join_member_with_days),
                    days
                )
            }
        })
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(context, 3)

        btVip.text = String.format(
            context.getString(R.string.join_member_with_days),
            "${list[0].periodValidity}天"
        )
        btVip.setOnClickListener {
            iClick.click(list[adapter.getSelectedIndex()].configId)
        }
    }

    interface IClick {
        fun click(configId: Int)
    }
}

class VipAdapter(
    private var context: Context,
    private var list: List<VipBean>,
    layoutId: Int,
) : BaseAdapter<VipBean, ViewDataBinding>(
    context, list, layoutId
) {
    private var selectIndex = 0
    private var iClick: IClick? = null

    init {
        list[0].isSelected = true
    }

    override fun onBind(holder: RecyclerHolder<ViewDataBinding>, position: Int) {
        holder.itemView.findViewById<ConstraintLayout>(R.id.cl_item).background =
            ContextCompat.getDrawable(
                context,
                if (list[position].isSelected) R.drawable.vip_empty_green_3 else R.drawable.vip_empty_grey_3
            )


        holder.itemView.findViewById<TextView>(R.id.tv_discount_price).text =
            "${list[position].discountPrice}元"
        holder.itemView.findViewById<TextView>(R.id.tv_price).text = "${list[position].price}元"
        holder.itemView.findViewById<TextView>(R.id.tv_time).text =
            "${list[position].periodValidity}天"

        holder.itemView.findViewById<ConstraintLayout>(R.id.cl_item).setOnClickListener {
            if (!list[position].isSelected) {
                selectIndex = position

                for (index in list.indices) {
                    list[index].isSelected = index == position
                }
                iClick?.click(
                    position,
                    holder.itemView.findViewById<TextView>(R.id.tv_time).text.toString()
                )
                notifyDataSetChanged()

            }
        }


    }

    fun getSelectedIndex(): Int {
        return selectIndex
    }

    fun setClickListener(iClick: IClick) {
        this.iClick = iClick
    }

    interface IClick {
        fun click(index: Int, days: String)
    }
}
