package com.kang.resume.resume.base

import android.app.Activity
import android.view.View
import com.kang.resume.R
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.EventMutableLiveData
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.event.UpdateResumeLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IHttp
import com.kang.resume.pro.IViewModel
import com.lxj.xpopup.XPopup
import java.lang.Exception
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
open class BaseResumeViewModel() : BaseViewModel() {

    var needDelete = EventMutableLiveData<Boolean>()

    //保存
    fun keep(iKeep: IKeep) {
        launch(object : IViewModel<Any> {
            override suspend fun launch(): ApiResponse<Any> {
                return iKeep.keep()
            }
        }, (object : IHttp<Any> {
            override suspend fun success(data: Any?) {
                finishLiveData.postValue(true)
                UpdateResumeLiveData.getInstance().postValue(true)
            }

            override suspend fun failure(response: ApiResponse<Any>) {}
        }))
    }

    //删除
    fun delete(activity: Activity, iDelete: IDelete) {
        XPopup.Builder(activity)
            .isDestroyOnDismiss(true)
            .asConfirm(
                activity.getString(R.string.tip),
                activity.getString(R.string.tip_delete),
                activity.getString(R.string.cancel),
                activity.getString(R.string.confirm),
                {
                    launch(object : IViewModel<Any> {
                        override suspend fun launch(): ApiResponse<Any> {
                            return iDelete.delete()
                        }
                    }, (object : IHttp<Any> {
                        override suspend fun success(data: Any?) {
                            finishLiveData.postValue(true)
                            UpdateResumeLiveData.getInstance().postValue(true)
                        }

                        override suspend fun failure(response: ApiResponse<Any>) {}
                    }))
                }, null, false
            ).show()


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

interface IKeep {
    suspend fun keep(): ApiResponse<Any>
}

interface IDelete {
    suspend fun delete(): ApiResponse<Any>
}
