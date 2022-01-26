package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.*
import com.kang.resume.databinding.ResumeTagActivityBinding
import com.kang.resume.databinding.ResumeWriteActivityBinding
import com.kang.resume.event.LoginBean
import com.kang.resume.event.LoginLiveData
import com.kang.resume.event.WriteLiveBean
import com.kang.resume.event.WriteLiveData
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IClick
import com.kang.resume.resume.base.IDelete
import com.kang.resume.resume.base.IKeep
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.VerifyUtils

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.WriteRouter)
class WriteContentActivity : BaseActivity<ResumeWriteActivityBinding, WriteContentModel>() {
    override fun initLayout(): Int {
        return R.layout.resume_write_activity
    }

    override fun ResumeWriteActivityBinding.initBinding() {
        when (mVm.from) {
            RouterConfig.SelfEvaluationFrom -> {
                mBinding.titleView.setTitle(getString(R.string.evaluation_info))
                mBinding.etContent.hint = getString(R.string.hint_evaluation_info)
            }
            RouterConfig.EducationFrom -> {
                mBinding.titleView.setTitle(getString(R.string.experience_at_school))
                mBinding.etContent.hint = getString(R.string.hint_education_experience)

            }
            RouterConfig.WorkFrom -> {
                mBinding.titleView.setTitle(getString(R.string.work_content))
                mBinding.etContent.hint = getString(R.string.hint_work_content)

            }
            RouterConfig.ProjectFrom -> {
                mBinding.titleView.setTitle(getString(R.string.project_description))
                mBinding.etContent.hint = getString(R.string.hint_project_description)

            }
        }

        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                when (mVm.from) {
                    RouterConfig.SelfEvaluationFrom -> {
                        val selfEvaluationBean =
                            SelfEvaluationBean(mVm.resumeId, mBinding.etContent.text.toString())

                        mVm.keep(object : IKeep {
                            override suspend fun keep(): ApiResponse<Any> {
                                return HttpRequest.api()
                                    .saveOrUpdateSelfEvaluation(selfEvaluationBean)
                            }
                        })
                    }
                    else -> {
                        finish()
                        WriteLiveData.getInstance().value = WriteLiveBean(mVm.from, mBinding.etContent.text.toString())
//                        WriteLiveData.getInstance()
//                            .postValue(WriteLiveBean(mVm.from, mBinding.etContent.text.toString()))
                    }
                }

            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity, object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api()
                        .delSelfEvaluation(mVm.resumeId!!)
                }
            })
        }
    }

    override fun initViewModel(): WriteContentModel {
        val content: String? = getOtherData()
        val resumeInfoBean: ResumeInfoBean? = getData()
        val from = intent.extras!!.getString(RouterConfig.from)

        initData(content)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                WriteContentModel(from, resumeInfoBean, content)
            )
        return mBinding.vm!!
    }

    private fun initData(content: String?) {
        if (content != null) {
            mBinding.etContent.setText(content)
        }
    }

    override fun isInput(): Boolean {
        return mVm.content != mBinding.etContent.text.toString()
    }
}