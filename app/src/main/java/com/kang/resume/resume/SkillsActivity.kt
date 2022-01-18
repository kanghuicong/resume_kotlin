package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ValueConfig
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.JobIntentionBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.SkillBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.databinding.ResumeSkillsActivityBinding
import com.kang.resume.databinding.ResumeWorkActivityBinding
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IClick
import com.kang.resume.resume.base.IDelete
import com.kang.resume.resume.base.IKeep
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.VerifyUtils
import com.lxj.xpopup.XPopup
import com.vondear.rxtool.view.RxToast

/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.SkillsRouter)
class SkillsActivity : BaseActivity<ResumeSkillsActivityBinding, SkillsModel>() {
    override fun initLayout(): Int {
        return R.layout.resume_skills_activity
    }


    override fun ResumeSkillsActivityBinding.initBinding() {
        //到岗时间
        mBinding.inputProficiency.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.skill_proficiency),
                        arrayOf(
                            getString(R.string.proficiency_generally),
                            getString(R.string.proficiency_good),
                            getString(R.string.proficiency_skilled),
                            getString(R.string.proficiency_proficient)
                        ),
                        null,
                        mVm.proficiencyIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.proficiencyIndex.value = position
                            mBinding.inputProficiency.setInput(text)
                        }
                    }.show()
            }
        })

        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                if (VerifyUtils.isEmpty(mBinding.llCheck)) {
                    return
                }

                val skillBean = SkillBean(
                    skillId = mVm.skillBean.skillId,
                    resumeId = mVm.skillBean.resumeId,
                    skillName = mBinding.inputSkill.getText(),
                    proficiency = mBinding.inputProficiency.getText(),
                    proficiencyValue = ValueConfig.proficiencyValueList[mVm.proficiencyIndex.value!!]
                )

                mVm.keep(object : IKeep {
                    override suspend fun keep(): ApiResponse<Any> {
                        return HttpRequest.api().saveOrUpdateSkill(skillBean)
                    }
                })

            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity, object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api().delSkill(mVm.skillBean.skillId!!)
                }
            })
        }
    }

    override fun initViewModel(): SkillsModel {
        val skillBean: SkillBean? = getOtherData()
        val resumeInfoBean: ResumeInfoBean? = getData()

        initData(skillBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                SkillsModel(resumeInfoBean, skillBean)
            )
        return mBinding.vm!!
    }

    private fun initData(skillBean: SkillBean?) {
        if (skillBean != null) {
            mBinding.inputSkill.setInput(skillBean.skillName)
            mBinding.inputProficiency.setInput(skillBean.proficiency)
        }
    }

    override fun isInput(): Boolean {
        return !(mVm.skillBean.skillName == mBinding.inputSkill.getText() &&
                mVm.skillBean.proficiency == mBinding.inputProficiency.getText())
    }
}