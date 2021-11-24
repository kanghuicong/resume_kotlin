package com.kang.resume.resume

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.elvishew.xlog.XLog
import com.kang.resume.R
import com.kang.resume.base.BaseFragment
import com.kang.resume.base.BaseViewModel
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.databinding.ResumeFragmentBinding
import com.kang.resume.event.LoginLiveData
import com.kang.resume.event.UpdateResumeLiveData
import com.kang.resume.pro.ICallBack
import com.kang.resume.pro.IClick
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import kotlinx.coroutines.launch

/**
 * 类描述：
 * author:kanghuicong
 */
class ResumeInfoFragment : BaseFragment<ResumeFragmentBinding, ResumeInfoModel>() {

    override fun initLayout(): Int {
        return R.layout.resume_fragment
    }

    override fun initViewCreated(view: View) {

        UpdateResumeLiveData.getInstance().observe(this, {
            mVm.viewModelScope.launch {
                if (it) {
                    mVm.queryResumeInfoList()
                }
            }
        })

        //新增一份简历
        mBinding.tvCreate.setOnClickListener {
            mVm.createResume()
        }

        //删除一份简历
        mBinding.btDeleteResume.setOnClickListener {
            mVm.deleteResume()
        }

        //基本信息
        mBinding.infoBase.iClick = (object : IClick {
            override fun click(view: View) {
                checkData(object : ICallBack {
                    override fun callBack() {
                        RouterNavigation.doIntentActivity(
                            RouterConfig.BaseInfoRouter,
                            mVm.resumeInfo
                        )
                    }
                })
            }
        })

        //求职信息
        mBinding.infoJob.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //教育经历
        mBinding.infoEducation.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //工作经历
        mBinding.infoWork.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //项目经验
        mBinding.infoProject.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //专业技能
        mBinding.infoSkill.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //证书奖项
        mBinding.infoCertificate.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //兴趣爱好
        mBinding.infoHobby.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })

        //自我评价
        mBinding.infoEvaluation.iClick = (object : IClick {
            override fun click(view: View) {

            }
        })
    }

    private fun checkData(iCallBack: ICallBack) {
        //该简历未创建成功
        if (mVm.resumeInfo.resumeId == null) {
            mVm.queryResumeInfoList()
        } else {
            iCallBack.callBack()
        }
    }

    override fun initViewModel(): ResumeInfoModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, ResumeInfoModel())
        return mBinding.vm!!
    }

}