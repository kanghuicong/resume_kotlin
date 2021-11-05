package com.kang.resume

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 类描述：
 * author:kanghuicong
 */

class MainViewModel() : ViewModel() {

    var index = MutableLiveData(0)

    var drawableTemplate = MutableLiveData<Int>()
    var drawableResume = MutableLiveData<Int>()
    var drawableMine = MutableLiveData<Int>()

    init {
        drawableTemplate.value = R.mipmap.icon_select_0
        drawableResume.value = R.mipmap.icon_disselect_1
        drawableMine.value = R.mipmap.icon_disselect_2
    }

    fun click(index: Int) {
        this.index.value = index

        drawableTemplate.value =
            if (index == 0) R.mipmap.icon_select_0 else R.mipmap.icon_disselect_0
        drawableResume.value = if (index == 1) R.mipmap.icon_select_1 else R.mipmap.icon_disselect_1
        drawableMine.value = if (index == 2) R.mipmap.icon_select_2 else R.mipmap.icon_disselect_2

    }


}