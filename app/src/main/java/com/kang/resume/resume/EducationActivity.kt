package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.databinding.ResumeEdcationActivityBinding
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IClick
import com.kang.resume.resume.base.IDelete
import com.kang.resume.resume.base.IKeep
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.VerifyUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.vondear.rxtool.view.RxToast
import java.util.*

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.EducationRouter)
class EducationActivity : BaseActivity<ResumeEdcationActivityBinding, EducationModel>() {
    override fun initLayout(): Int {
        return R.layout.resume_edcation_activity
    }

    override fun ResumeEdcationActivityBinding.initBinding() {
        //选择学历
        mBinding.inputRecord.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.record),
                        arrayOf(
                            getString(R.string.junior_high_school),
                            getString(R.string.high_school),
                            getString(R.string.technical_secondary_school),
                            getString(R.string.junior_college),
                            getString(R.string.undergraduate),
                            getString(R.string.master),
                            getString(R.string.doctor),
                            getString(R.string.postdoc),
                            getString(R.string.other),
                        ),
                        null,
                        mVm.recordIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.recordIndex.value = position
                            mBinding.inputRecord.setInput(text)
                        }
                    }.show()
            }
        })

        //选择开始时间
        mBinding.inputStartTime.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asCustom(
                        TimePickerPopup(activity)
                            .setDefaultDate(mVm.startTimeDefaultDate)  //设置默认选中日期
                            .setTimePickerListener(object : TimePickerListener {
                                override fun onTimeChanged(date: Date?) {
                                    //时间改变
                                }

                                override fun onTimeConfirm(date: Date, view: View?) {
                                    val time = mVm.switchDate(date)
                                    mBinding.inputStartTime.setInput(time)

                                    mVm.switchTime(
                                        mVm.startTimeDefaultDate,
                                        time
                                    )
                                }
                            })
                    ).show()
            }
        })

        //选择结束时间
        mBinding.inputEndTime.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asCustom(
                        TimePickerPopup(activity)
                            .setDefaultDate(mVm.endTimeDefaultDate)  //设置默认选中日期
                            .setTimePickerListener(object : TimePickerListener {
                                override fun onTimeChanged(date: Date?) {
                                    //时间改变
                                }

                                override fun onTimeConfirm(date: Date, view: View?) {
                                    val time = mVm.switchDate(date)
                                    mBinding.inputEndTime.setInput(time)

                                    mVm.switchTime(
                                        mVm.endTimeDefaultDate,
                                        time
                                    )
                                }
                            })
                    ).show()
            }
        })

        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                if (VerifyUtils.isEmpty(mBinding.llCheck)) {
                    return
                }


                val educationBean = EducationBean(
                    mVm.educationBean.educationId,
                    mVm.educationBean.resumeId,
                    mBinding.inputSchool.getText(),
                    mBinding.inputMajor.getText(),
                    mBinding.inputRecord.getText(),
                    mBinding.inputStartTime.getText(),
                    mBinding.inputEndTime.getText(),
                    mBinding.inputExperience.getText(),
                )

                mVm.keep(object : IKeep {
                    override suspend fun keep(): ApiResponse<Any> {
                        return HttpRequest.api().saveOrUpdateEducation(educationBean)
                    }
                })
            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity,object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api().delEducation(mVm.educationBean.educationId!!)
                }
            })
        }
    }


    override fun initViewModel(): EducationModel {
        val educationBean: EducationBean? = getOtherData()
        val resumeInfoBean: ResumeInfoBean? = getData()

        initData(educationBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                EducationModel(resumeInfoBean,educationBean)
            )
        return mBinding.vm!!
    }

    private fun initData(educationBean: EducationBean?) {
        if (educationBean != null) {
            mBinding.inputSchool.setInput(educationBean.school)
            mBinding.inputRecord.setInput(educationBean.record)
            mBinding.inputMajor.setInput(educationBean.major)
            mBinding.inputStartTime.setInput(educationBean.startTime)
            mBinding.inputEndTime.setInput(educationBean.endTime)
            mBinding.inputExperience.setInput(educationBean.experience)
        }
    }

    override fun isInput(): Boolean {
        return true
    }
}