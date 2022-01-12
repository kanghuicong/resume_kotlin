package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.databinding.ResumeWorkActivityBinding
import com.kang.resume.event.WriteLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IClick
import com.kang.resume.resume.base.IDelete
import com.kang.resume.resume.base.IKeep
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
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
@Route(path = RouterConfig.WorkRouter)
class WorkActivity : BaseActivity<ResumeWorkActivityBinding, WorkModel>() {
    override fun initLayout(): Int {
        return R.layout.resume_work_activity
    }

    override fun ResumeWorkActivityBinding.initBinding() {
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

        //工作内容
        mBinding.inputContent.iClick = (object : IClick {
            override fun click(view: View) {
                RouterNavigation.doIntentActivity(
                    RouterConfig.WriteRouter,
                    RouterConfig.WorkFrom,
                    null,
                    mBinding.inputContent.getText()
                )
            }
        })
        WriteLiveData.getInstance().observe(activity, {
            if (it.from == RouterConfig.WorkFrom){
                mBinding.inputContent.setInput(it.content)
                finish()
            }
        })

        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                if (VerifyUtils.isEmpty(mBinding.llCheck)) {
                    return
                }

                val workExperienceBean = WorkExperienceBean(
                    mVm.workExperienceBean.experienceId,
                    mVm.workExperienceBean.resumeId,
                    mBinding.inputCompany.getText(),
                    mBinding.inputPosition.getText(),
                    mBinding.inputStartTime.getText(),
                    mBinding.inputEndTime.getText(),
                    mBinding.inputContent.getText(),
                )

                mVm.keep(object : IKeep {
                    override suspend fun keep(): ApiResponse<Any> {
                        return HttpRequest.api().saveOrUpdateWorkExperience(workExperienceBean)
                    }
                })
            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity, object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api()
                        .delWorkExperience(mVm.workExperienceBean.experienceId!!)
                }
            })
        }

    }

    override fun initViewModel(): WorkModel {
        val workExperienceBean: WorkExperienceBean? = getOtherData()
        val resumeInfoBean: ResumeInfoBean? = getData()

        initData(workExperienceBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                WorkModel(resumeInfoBean, workExperienceBean)
            )
        return mBinding.vm!!
    }

    private fun initData(workExperienceBean: WorkExperienceBean?) {
        if (workExperienceBean != null) {
            mBinding.inputCompany.setInput(workExperienceBean.company)
            mBinding.inputPosition.setInput(workExperienceBean.position)
            mBinding.inputStartTime.setInput(workExperienceBean.startTime)
            mBinding.inputEndTime.setInput(workExperienceBean.endTime)
            mBinding.inputContent.setInput(workExperienceBean.workContent)
        }
    }


    override fun isInput(): Boolean {
        return true
    }
}