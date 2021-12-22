package com.kang.resume.resume

import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ValueConfig
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.custom.CityPickerPopup
import com.kang.resume.databinding.ResumeJobActivityBinding
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IClick
import com.kang.resume.resume.base.IDelete
import com.kang.resume.resume.base.IKeep
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.ToastUtil
import com.kang.resume.utils.VerifyUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.CityPickerListener


/**
 * 类描述：求职信息
 * author:kanghuicong
 */
@Route(path = RouterConfig.JobRouter)
class JobActivity : BaseActivity<ResumeJobActivityBinding, JobModel>() {

    override fun ResumeJobActivityBinding.initBinding() {

        //期望城市
        val popup = CityPickerPopup(activity)
        popup.setCityPickerListener(object : CityPickerListener {
            override fun onCityConfirm(
                province: String,
                city: String,
                area: String,
                v: View
            ) {
                mBinding.inputCity.setInput("$province${ValueConfig.space}$city")
            }

            override fun onCityChange(province: String, city: String, area: String) {}
        })
        mBinding.inputCity.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .asCustom(popup)
                    .show()
            }
        })

        //到岗时间
        mBinding.inputTime.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.job_time),
                        arrayOf(
                            getString(R.string.job_time_anytime),
                            getString(R.string.job_time_7_days),
                            getString(R.string.job_time_1_month),
                            getString(R.string.job_time_3_month),
                            getString(R.string.job_time_no)
                        ),
                        null,
                        mVm.timeIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.timeIndex.value = position
                            mBinding.inputTime.setInput(text)
                        }
                    }.show()
            }
        })


        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                if (VerifyUtils.isEmpty(mBinding.inputPosition.getText())) {
                    ToastUtil.show(getString(R.string.hint_job_position))
                    return
                }

                if (VerifyUtils.isEmpty(mBinding.inputCity.getText())) {
                    ToastUtil.show(getString(R.string.hint_job_city))
                    return
                }

                if (VerifyUtils.isEmpty(mBinding.inputTime.getText())) {
                    ToastUtil.show(getString(R.string.hint_job_time))
                    return
                }

                if (VerifyUtils.isEmpty(mBinding.etStartSalary.text.toString()) || VerifyUtils.isEmpty(
                        mBinding.etEndSalary.text.toString()
                    )
                ) {
                    ToastUtil.show(getString(R.string.hint_job_salary))
                    return
                }

                val jobIntentionBean = JobIntentionBean(
                    mVm.jobIntentionBean.id,
                    mVm.jobIntentionBean.resumeId,
                    mBinding.inputCity.getText(),
                    mBinding.inputTime.getText(),
                    mBinding.inputPosition.getText(),
                    "${mBinding.etStartSalary.text}k${ValueConfig.space}${mBinding.etEndSalary.text}k",
                )

                mVm.keep(object : IKeep {
                    override suspend fun keep(): ApiResponse<Any> {
                        return HttpRequest.api().saveOrUpdateJobIntention(jobIntentionBean)
                    }
                })

            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity, object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api().delJobIntention(mVm.jobIntentionBean.resumeId!!)
                }
            })
        }
    }

    override fun isInput(): Boolean {
        return true
    }

    override fun initLayout(): Int {
        return R.layout.resume_job_activity
    }

    override fun initViewModel(): JobModel {
        val resumeInfoBean: ResumeInfoBean? = getData()

        initData(resumeInfoBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                JobModel(resumeInfoBean)
            )
        return mBinding.vm!!
    }

    private fun initData(resumeInfoBean: ResumeInfoBean?) {
        val jobIntentionBean = resumeInfoBean?.jobIntention
        if (jobIntentionBean != null) {
            mBinding.inputTime.setInput(jobIntentionBean.entryTime)
            mBinding.inputPosition.setInput(jobIntentionBean.position)
            mBinding.inputCity.setInput(jobIntentionBean.city)

            val list = jobIntentionBean.salary.split(ValueConfig.space)
            if (list.size == 2) {
                val start = list[0].replace("k", "")
                val end = list[1].replace("k", "")
                mBinding.etStartSalary.setText(start)
                mBinding.etEndSalary.setText(end)
            }
        }
    }

}