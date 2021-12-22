package com.kang.resume.resume

import android.view.View
import androidx.lifecycle.viewModelScope
import com.kang.resume.R
import com.kang.resume.base.BaseFragment
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.EducationBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.custom.info.BaseInfoWidget
import com.kang.resume.custom.info.EducationWidget
import com.kang.resume.custom.info.InfoTitleView
import com.kang.resume.custom.info.PositionWidget
import com.kang.resume.databinding.ResumeFragmentBinding
import com.kang.resume.event.UpdateResumeLiveData
import com.kang.resume.pro.ICallBack
import com.kang.resume.pro.IClick
import com.kang.resume.pro.IWidget
import com.kang.resume.router.RouterConfig
import com.kang.resume.router.RouterNavigation
import com.lxj.xpopup.XPopup
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
            XPopup.Builder(context)
                .isDestroyOnDismiss(true)
                .asConfirm(
                    getString(R.string.tip),
                    getString(R.string.tip_delete_resume),
                    getString(R.string.cancel),
                    getString(R.string.confirm),
                    {
                        mVm.deleteResume()
                    }, null, false
                ).show()
        }

        //基本信息
        mBinding.infoBase.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.BaseInfoRouter)
            }
        })

        //求职信息
        mBinding.infoJob.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.JobRouter)
            }
        })

        //教育经历
        mBinding.infoEducation.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.EducationRouter, null)
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

        //各个栏目卡片数据
        initAllData(mVm.resumeInfo.value)
        //更新卡片数据
        mVm.resumeInfo.observe(this, {
            initAllData(it)
        })
    }


    //获取所有卡片数据
    private fun initAllData(resumeInfoBean: ResumeInfoBean?) {
        if (resumeInfoBean == null) return

        mVm.initData(mBinding.infoBase, BaseInfoWidget(activity), resumeInfoBean.baseInfo)
        mVm.initData(mBinding.infoJob, PositionWidget(activity), resumeInfoBean.jobIntention)

        val views = ArrayList<View>()
        for (education: EducationBean in resumeInfoBean.educations!!) {
            val iWidget = EducationWidget(activity)
            iWidget.setData(education)
            views.add(iWidget.getView())
        }
        mBinding.infoEducation.setChildView(views)

    }


    override fun initViewModel(): ResumeInfoModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, ResumeInfoModel())
        return mBinding.vm!!
    }

}