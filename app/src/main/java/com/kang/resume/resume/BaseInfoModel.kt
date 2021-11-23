package com.kang.resume.resume

import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.ResumeInfoBean
import java.lang.Exception
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
class BaseInfoModel(resumeInfoBean: ResumeInfoBean?) : BaseViewModel() {

    //性别 弹窗下标
    var genderIndex = EventMutableLiveData<Int>()

    //出生年月 弹窗默认数据
    var birthdayDefaultDate: Calendar = Calendar.getInstance()

    //参加工作时间 弹窗默认数据
    var startWorkTimeDefaultDate: Calendar = Calendar.getInstance()


    var baseInfoBean = BaseInfoBean(null, null)

    init {
        //赋值操作
        //....
        if (resumeInfoBean?.baseInfo != null) {
            baseInfoBean = resumeInfoBean.baseInfo!!
        }

        //数据处理
        //gender 1:男 对应genderIndex 0
        //gender 2:女 对应genderIndex 1
        when (baseInfoBean.gender) {
            getString(R.string.gender_man) -> genderIndex.value = 0
            getString(R.string.gender_women) -> genderIndex.value = 1
            else -> genderIndex.value = -1
        }

        birthdayDefaultDate = switchTime(birthdayDefaultDate, baseInfoBean.birthday)
        startWorkTimeDefaultDate = switchTime(startWorkTimeDefaultDate, baseInfoBean.startWorkTime)

    }


    fun switchDate(date: Date): String {
        return try {
            //2020年12月12日 12时12分12秒
            val dataTime = date.toLocaleString()
            //2020年12月12日
            val ary = dataTime.split(" ")

            ary[0]
        } catch (e: Exception) {
            ""
        }
    }

    fun switchTime(calendar: Calendar, time: String?): Calendar {
        if (time == null || time == "") return calendar
        try {
            var mTime = time
            mTime = mTime.replace("年", "-")
            mTime = mTime.replace("月", "-")
            mTime = mTime.replace("日", "")

            val dateAry = mTime.split("-")

            calendar.set(
                dateAry[0].toInt(),
                dateAry[1].toInt() - 1,
                dateAry[2].toInt()
            )

            return calendar
        } catch (e: Exception) {
            return calendar
        }
    }
}