package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.ProjectBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.databinding.ResumeJobActivityBinding
import com.kang.resume.databinding.ResumeProjectActivityBinding
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
@Route(path = RouterConfig.ProjectRouter)
class ProjectActivity : BaseActivity<ResumeProjectActivityBinding, ProjectModel>() {
    override fun initLayout(): Int {
        return R.layout.resume_project_activity
    }

    override fun ResumeProjectActivityBinding.initBinding() {
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

        //项目描述
        mBinding.inputDescription.iClick = (object : IClick {
            override fun click(view: View) {
                RouterNavigation.doIntentActivity(
                    RouterConfig.WriteRouter,
                    RouterConfig.ProjectFrom,
                    null,
                    mBinding.inputDescription.getText()
                )
            }
        })

        WriteLiveData.getInstance().observe(activity, {
            if (it.from == RouterConfig.ProjectFrom) {
                mBinding.inputDescription.setInput(it.content)
                finish()
            }
        })


        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                if (VerifyUtils.isEmpty(mBinding.llCheck)) {
                    return
                }

                val projectBean = ProjectBean(
                    projectId = mVm.projectBean.projectId,
                    resumeId = mVm.projectBean.resumeId,
                    projectName = mBinding.inputName.getText(),
                    company = mBinding.inputCompany.getText(),
                    role = mBinding.inputRole.getText(),
                    startTime = mBinding.inputStartTime.getText(),
                    endTime = mBinding.inputEndTime.getText(),
                    description = mBinding.inputDescription.getText(),
                    url = mBinding.inputUrl.getText(),
                )

                mVm.keep(object : IKeep {
                    override suspend fun keep(): ApiResponse<Any> {
                        return HttpRequest.api().saveOrUpdateProject(projectBean)
                    }
                })
            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity, object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api().delProject(mVm.projectBean.projectId!!)
                }
            })
        }
    }

    override fun initViewModel(): ProjectModel {
        val projectBean: ProjectBean? = getOtherData()
        val resumeInfoBean: ResumeInfoBean? = getData()

        initData(projectBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                ProjectModel(resumeInfoBean, projectBean)
            )
        return mBinding.vm!!
    }

    private fun initData(data: ProjectBean?) {
        if (data != null) {
            mBinding.inputName.setInput(data.projectName)
            mBinding.inputCompany.setInput(data.company)
            mBinding.inputRole.setInput(data.role)
            mBinding.inputStartTime.setInput(data.startTime)
            mBinding.inputEndTime.setInput(data.endTime)
            mBinding.inputDescription.setInput(data.description)
            mBinding.inputUrl.setInput(data.url)
        }
    }

    override fun isInput(): Boolean {
        return !(mVm.projectBean.projectName == mBinding.inputName.getText() &&
                mVm.projectBean.company == mBinding.inputCompany.getText() &&
                mVm.projectBean.role == mBinding.inputRole.getText() &&
                mVm.projectBean.startTime == mBinding.inputStartTime.getText() &&
                mVm.projectBean.endTime == mBinding.inputEndTime.getText() &&
                mVm.projectBean.description == mBinding.inputDescription.getText() &&
                mVm.projectBean.url == mBinding.inputUrl.getText())
    }
}