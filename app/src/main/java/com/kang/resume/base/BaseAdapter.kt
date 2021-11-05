package com.kang.resume.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kang.resume.BR


/**
 * 类描述：
 * author:kanghuicong
 */
abstract class BaseAdapter<T, VB : ViewDataBinding>(
    private var context: Context,
    private var list: List<T>?,
    @LayoutRes private var layoutId: Int
) : RecyclerView.Adapter<RecyclerHolder<VB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder<VB> {
        val bind =
            DataBindingUtil.inflate<VB>(LayoutInflater.from(context), layoutId, parent, false)

        return RecyclerHolder(bind)
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder<VB>, position: Int) {
        holder.binding?.setVariable(BR.item, list!![position])
        holder.binding?.executePendingBindings()

        onBind(holder, position)
    }

    abstract fun onBind(holder: RecyclerHolder<VB>, position: Int)
}


class RecyclerHolder<VB : ViewDataBinding?>(binding: VB) :
    RecyclerView.ViewHolder(binding!!.root) {

    var binding: VB? = null

    init {
        this.binding = binding
    }
}