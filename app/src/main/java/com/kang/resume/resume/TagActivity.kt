package com.kang.resume.resume

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.CertificateBean
import com.kang.resume.bean.HobbyBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.bean.WorkExperienceBean
import com.kang.resume.custom.info.TagsAdapter
import com.kang.resume.databinding.ResumeTagActivityBinding
import com.kang.resume.databinding.ResumeWorkActivityBinding
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
@Route(path = RouterConfig.TagRouter)
class TagActivity : BaseActivity<ResumeTagActivityBinding, TagModel>() {

    var from: String? = null

    override fun initLayout(): Int {
        return R.layout.resume_tag_activity
    }

    override fun ResumeTagActivityBinding.initBinding() {

        var title = ""
        var inputTitle = ""
        var inputHint = ""
        var list = mutableListOf<String>()
        if (RouterConfig.CertificateFrom == from) {
            title = getString(R.string.certificate_info)
            inputTitle = getString(R.string.certificate_info)
            inputHint = getString(R.string.hint_certificate_info)
            list.add("英语四级")
        } else if (RouterConfig.HobbyFrom == from) {
            title = getString(R.string.hobby_info)
            inputTitle = getString(R.string.hobby_info)
            inputHint = getString(R.string.hint_hobby_info)
            list.add("篮球")
        }

        val tagsAdapter = TagsAdapter(list)
        tagsAdapter.iTagsClick = (object : TagsAdapter.ITagsClick {
            override fun click(view: View, str: Any?) {
                mBinding.inputTag.setInput(str!! as String)
            }

        })
        mBinding.llTag.adapter = tagsAdapter

        mBinding.titleView.setTitle(title)
        mBinding.inputTag.setTitle(inputTitle)
        mBinding.inputTag.setHint(inputHint)


        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                if (VerifyUtils.isEmpty(mBinding.llCheck)) {
                    return
                }

                if (RouterConfig.CertificateFrom == from) {
                    val certificateBean = CertificateBean(
                        certificateId = mVm.certificateBean.certificateId,
                        resumeId = mVm.certificateBean.resumeId,
                        certificate = mBinding.inputTag.getText(),
                    )
                    mVm.keep(object : IKeep {
                        override suspend fun keep(): ApiResponse<Any> {
                            return HttpRequest.api().saveOrUpdateCertificate(certificateBean)
                        }
                    })
                } else if (RouterConfig.HobbyFrom == from) {
                    val hobbyBean = HobbyBean(
                        hobbyId = mVm.hobbyBean.hobbyId,
                        resumeId = mVm.hobbyBean.resumeId,
                        hobby = mBinding.inputTag.getText(),
                    )
                    mVm.keep(object : IKeep {
                        override suspend fun keep(): ApiResponse<Any> {
                            return HttpRequest.api().saveOrUpdateHobby(hobbyBean)
                        }
                    })
                }
            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            if (RouterConfig.CertificateFrom == from) {
                mVm.delete(activity, object : IDelete {
                    override suspend fun delete(): ApiResponse<Any> {
                        return HttpRequest.api()
                            .delCertificate(mVm.certificateBean.certificateId!!)
                    }
                })
            } else if (RouterConfig.HobbyFrom == from) {
                mVm.delete(activity, object : IDelete {
                    override suspend fun delete(): ApiResponse<Any> {
                        return HttpRequest.api()
                            .delHobby(mVm.hobbyBean.hobbyId!!)
                    }
                })
            }

        }
    }

    override fun initViewModel(): TagModel {
        val resumeInfoBean: ResumeInfoBean? = getData()
        from = intent.extras!!.getString(RouterConfig.from)

        var data: Any? = null
        if (from == RouterConfig.CertificateFrom) {
            data = getOtherData<CertificateBean>()
        } else if (from == RouterConfig.HobbyFrom) {
            data = getOtherData<HobbyBean>()
        }

        initData(data)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                TagModel(from, resumeInfoBean, data)
            )
        return mBinding.vm!!
    }

    private fun initData(data: Any?) {
        if (data != null) {
            if (data is CertificateBean) {
                mBinding.inputTag.setInput(data.certificate)
            } else if (data is HobbyBean) {
                mBinding.inputTag.setInput(data.hobby)
            }
        }
    }

    override fun isInput(): Boolean {
        if (from == RouterConfig.CertificateFrom) {
            return mVm.certificateBean.certificate != mBinding.inputTag.getText()
        } else if (from == RouterConfig.HobbyFrom) {
            return mVm.hobbyBean.hobby != mBinding.inputTag.getText()
        }
        return true
    }

}