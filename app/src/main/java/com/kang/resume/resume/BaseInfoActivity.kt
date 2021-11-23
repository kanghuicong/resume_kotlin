package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.elvishew.xlog.XLog
import com.google.gson.Gson
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.databinding.ResumeBaseInfoActivityBinding
import com.kang.resume.pro.IClick
import com.kang.resume.router.RouterConfig
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import java.util.*


/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.BaseInfoRouter)
class BaseInfoActivity : BaseActivity<ResumeBaseInfoActivityBinding, BaseInfoModel>() {

    override fun ResumeBaseInfoActivityBinding.initBinding() {


        mBinding.inputGender.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.gender),
                        arrayOf(
                            getString(R.string.gender_man),
                            getString(R.string.gender_women)
                        ),
                        null,
                        mVm.genderIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.genderIndex.value = position
                            mVm.baseInfoBean.gender = text
                            mBinding.inputGender.setInput(text)
                        }
                    }.show()
            }
        })


        mBinding.inputBirthday.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asCustom(
                        TimePickerPopup(activity)
                            .setDefaultDate(mVm.birthdayDefaultDate)  //设置默认选中日期
                            .setTimePickerListener(object : TimePickerListener {
                                override fun onTimeChanged(date: Date?) {
                                    //时间改变
                                }

                                override fun onTimeConfirm(date: Date, view: View?) {
                                    mVm.baseInfoBean.birthday = mVm.switchDate(date)
                                    mBinding.inputBirthday.setInput(mVm.baseInfoBean.birthday)

                                    mVm.switchTime(
                                        mVm.birthdayDefaultDate,
                                        mVm.baseInfoBean.birthday
                                    )
                                }
                            })
                    ).show()
            }
        })

        mBinding.inputStartWorkTime.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asCustom(
                        TimePickerPopup(activity)
                            .setDefaultDate(mVm.startWorkTimeDefaultDate)  //设置默认选中日期
                            .setTimePickerListener(object : TimePickerListener {
                                override fun onTimeChanged(date: Date?) {
                                    //时间改变
                                }

                                override fun onTimeConfirm(date: Date, view: View?) {
                                    //点击确认时间

                                    mVm.baseInfoBean.startWorkTime = mVm.switchDate(date)
                                    mBinding.inputStartWorkTime.setInput(mVm.baseInfoBean.startWorkTime)

                                    mVm.switchTime(
                                        mVm.startWorkTimeDefaultDate,
                                        mVm.baseInfoBean.startWorkTime
                                    )

                                }
                            })
                    ).show()
            }
        })
    }

    override fun initLayout(): Int {
        return R.layout.resume_base_info_activity
    }


    override fun initViewModel(): BaseInfoModel {
        val resumeInfoBean: ResumeInfoBean = formJson()

        initData(resumeInfoBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                BaseInfoModel(resumeInfoBean)
            )
        return mBinding.vm!!
    }

    //填入数据
    private fun initData(resumeInfoBean: ResumeInfoBean?) {
        val baseInfo = resumeInfoBean?.baseInfo
        if (baseInfo != null) {
            mBinding.inputName.setInput(baseInfo.name)
            mBinding.inputEmail.setInput(baseInfo.email)
            mBinding.inputStartWorkTime.setInput(baseInfo.startWorkTime)
            mBinding.inputBirthday.setInput(baseInfo.birthday)
            mBinding.inputPhone.setInput(baseInfo.phone)
            mBinding.inputGender.setInput(baseInfo.gender)
        }
    }

}

