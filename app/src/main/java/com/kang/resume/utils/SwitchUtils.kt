package com.kang.resume.utils

import com.elvishew.xlog.XLog
import java.text.SimpleDateFormat
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
object SwitchUtils {


    fun switchYear(birthdayStr: String?): String {
        val birthday = parseServerTime(birthdayStr)

        if (birthday == null) return "0"
        else {
            // 得到当前时间的年、月、日
            val cal = Calendar.getInstance()
            val yearNow = cal[Calendar.YEAR]
            val monthNow = cal[Calendar.MONTH] + 1
            val dayNow = cal[Calendar.DATE]
            //得到输入时间的年，月，日
            cal.time = birthday
            val selectYear = cal[Calendar.YEAR]
            val selectMonth = cal[Calendar.MONTH] + 1
            val selectDay = cal[Calendar.DATE]
            // 用当前年月日减去生日年月日
            val yearMinus = yearNow - selectYear
            val monthMinus = monthNow - selectMonth
            val dayMinus = dayNow - selectDay
            var age = yearMinus // 先大致赋值

            if (yearMinus <= 0) {
                age = 0;
            }
            if (monthMinus < 0) {
                age -= 1;
            } else if (monthMinus == 0) {
                if (dayMinus < 0) {
                    age -= 1;
                }
            }
            return age.toString()
        }


    }


    private fun parseServerTime(serverTime: String?): Date? {
        if (VerifyUtils.isEmpty(serverTime)) return null
        var time = serverTime
        time = time?.replace("年","-")
        time = time?.replace("月","-")
        time = time?.replace("日","-")

        val list = time?.split("-")
        if (list!![1].length==1){
            list[1].replace(list[1],"0${list[1]}")
        }
        if (list[2].length==1){
            list[2].replace(list[2],"0${list[2]}")
        }

        time = list[0]+"-"+list[1]+"-"+list[2]

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"))
        var date: Date? = null
        try {
            date = sdf.parse(time)
        } catch (e: Exception) {
            XLog.e("Exception:$e")
        }
        return date
    }
}