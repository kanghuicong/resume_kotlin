package com.kang.resume.resume

import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kang.resume.R
import com.kang.resume.adapter.IResumeClick
import com.kang.resume.adapter.ResumeTagAdapter
import com.kang.resume.base.BaseFragment
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.*
import com.kang.resume.custom.info.*
import com.kang.resume.databinding.ResumeFragmentBinding
import com.kang.resume.event.UpdateResumeLiveData
import com.kang.resume.pro.IClick
import com.kang.resume.router.RouterConfig
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.launch


/**
 * 类描述：
 * author:kanghuicong
 */
class ResumeInfoFragment : BaseFragment<ResumeFragmentBinding, ResumeInfoModel>() {

    var list: ArrayList<ResumeInfoBean>? = ArrayList()
    lateinit var adapter: ResumeTagAdapter

    override fun initLayout(): Int {
        return R.layout.resume_fragment
    }

    override fun initViewCreated(view: View) {

        //监听简历更新
        UpdateResumeLiveData.getInstance().observe(this, {
            mVm.viewModelScope.launch {
                if (it) {
                    mVm.queryResumeInfoList(false)
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
                mVm.doRouter(RouterConfig.EducationRouter)
            }
        })

        //工作经历
        mBinding.infoWork.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.WorkRouter)
            }
        })

        //项目经验
        mBinding.infoProject.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.ProjectRouter)
            }
        })

        //专业技能
        mBinding.infoSkill.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.SkillsRouter)
            }
        })

        //证书奖项
        mBinding.infoCertificate.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.TagRouter, RouterConfig.CertificateFrom)

            }
        })

        //兴趣爱好
        mBinding.infoHobby.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.TagRouter, RouterConfig.HobbyFrom)
            }
        })

        //自我评价
        mBinding.infoEvaluation.iClick = (object : IClick {
            override fun click(view: View) {
                mVm.doRouter(RouterConfig.WriteRouter, RouterConfig.SelfEvaluationFrom)
            }
        })

        //各个栏目卡片数据
        initAllData(mVm.resumeInfo.value)
        //更新卡片数据
        mVm.resumeInfo.observe(this, {
            initAllData(it)
            initList()
            adapter.notifyDataSetChanged()
        })



        initList()

        mBinding.rvResume.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        if (mVm.resumeInfoList?.size!! > 0) {
            mVm.resumeInfoList?.get(mVm.index)?.isClick = true
        }
        adapter = ResumeTagAdapter(activity, list, R.layout.item_resume_tag)
        //切换简历
        adapter.iResumeClick = (object : IResumeClick {
            override fun click(position: Int) {
                mVm.switchTag(position)
            }

            override fun add() {
                mVm.createResume()
            }
        })
        mBinding.rvResume.adapter = adapter


    }

    //添加加号
    private fun initList() {
        if (list != null) {
            list!!.clear()
            list!!.addAll(mVm.resumeInfoList!!)
        } else {
            list = mVm.resumeInfoList
        }

        if (list != null) {
            if (list!!.size < 3) {
                val resumeInfoBean = ResumeInfoBean(resumeId = -1)
                list!!.add(resumeInfoBean)
            }
        }
    }


    //获取所有卡片数据
    private fun initAllData(resumeInfoBean: ResumeInfoBean?) {
        if (resumeInfoBean == null) return

        resumeInfoBean.baseInfo?.resumeName = resumeInfoBean.resumeName
        mVm.initData(mBinding.infoBase, BaseInfoWidget(activity), resumeInfoBean.baseInfo)
        mBinding.infoBase.showAddImg(resumeInfoBean.baseInfo == null)

        mVm.initData(mBinding.infoJob, PositionWidget(activity), resumeInfoBean.jobIntention)
        mBinding.infoJob.showAddImg(resumeInfoBean.jobIntention == null)

        val educationViews = ArrayList<View>()
        for (index in resumeInfoBean.educations!!.indices) {
            val iWidget =
                EducationWidget(activity, index == resumeInfoBean.educations!!.indices.last)
            iWidget.setData(resumeInfoBean.educations!![index])
            educationViews.add(iWidget.getView())
        }

        mBinding.infoEducation.setChildView(educationViews)

        val workViews = ArrayList<View>()
        for (index in resumeInfoBean.workExperiences!!.indices) {
            val iWidget =
                WorkWidget(activity, index == resumeInfoBean.workExperiences!!.indices.last)
            iWidget.setData(resumeInfoBean.workExperiences!![index])
            workViews.add(iWidget.getView())
        }
        mBinding.infoWork.setChildView(workViews)

        val projectViews = ArrayList<View>()
        for (index in resumeInfoBean.projects!!.indices) {
            val iWidget = ProjectWidget(activity, index == resumeInfoBean.projects!!.indices.last)
            iWidget.setData(resumeInfoBean.projects!![index])
            projectViews.add(iWidget.getView())
        }
        mBinding.infoProject.setChildView(projectViews)

        val skillViews = ArrayList<View>()
        for (skill: SkillBean in resumeInfoBean.skills!!) {
            val iWidget = SkillsWidget(activity)
            iWidget.setData(skill)
            skillViews.add(iWidget.getView())
        }
        mBinding.infoSkill.setChildView(skillViews)

        mVm.initData(mBinding.infoCertificate, TagWidget(activity), resumeInfoBean.certificates)
        mVm.initData(mBinding.infoHobby, TagWidget(activity), resumeInfoBean.hobbies)

        mVm.initData(
            mBinding.infoEvaluation,
            SelfEvaluationWidget(activity, mVm.resumeInfo.value),
            resumeInfoBean.selfEvaluation
        )
        mBinding.infoEvaluation.showAddImg(resumeInfoBean.selfEvaluation.isNullOrEmpty())
    }


    override fun initViewModel(): ResumeInfoModel {
        mBinding.vm =
            ViewModelProviderFactory.getViewModel(this, ResumeInfoModel())
        return mBinding.vm!!
    }

}