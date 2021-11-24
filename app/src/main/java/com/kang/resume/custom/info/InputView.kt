package com.kang.resume.custom.info

import android.R.attr.maxLength
import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.kang.resume.R
import com.kang.resume.databinding.ViewInputBinding
import com.kang.resume.pro.IClick


/**
 * 类描述：
 * author:kanghuicong
 */
class InputView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var iClick: IClick? = null

    var vb: ViewInputBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_input,
        this,
        true
    )

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.InputView
        )

        vb.tvInputTitle.text = typedArray.getString(R.styleable.InputView_input_title)
        vb.etInput.hint = typedArray.getString(R.styleable.InputView_input_hint)
        vb.tvSubtitle.text = typedArray.getString(R.styleable.InputView_input_subtitle)

        val needArrow = typedArray.getBoolean(R.styleable.InputView_input_need_arrow, false)
        if (needArrow) {
            vb.ivArrow.visibility = View.VISIBLE
            vb.etInput.isFocusable = false
            vb.etInput.isCursorVisible = false
            vb.etInput.isLongClickable = false
            vb.etInput.setTextIsSelectable(false)

            vb.etInput.setOnClickListener {
                if (iClick != null)
                    iClick!!.click(vb.etInput)
            }
        } else {
            vb.ivArrow.visibility = View.GONE
        }

        val isNumber = typedArray.getBoolean(R.styleable.InputView_is_number, false)
        if (isNumber){
            vb.etInput.inputType = InputType.TYPE_CLASS_NUMBER
        }

        typedArray.recycle()
    }

    fun setInput(input: String): InputView {
        vb.etInput.setText(input)
        return this
    }

    fun getText(): String {
        return vb.etInput.text.toString()
    }

    fun setLength(length :Int): InputView{
        vb.etInput.filters = arrayOf<InputFilter>(LengthFilter(length))
        return this
    }

}