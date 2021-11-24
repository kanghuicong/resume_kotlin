package com.kang.resume.resume

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kang.resume.R
import com.kang.resume.base.BaseActivity
import com.kang.resume.base.ViewModelProviderFactory
import com.kang.resume.bean.BaseInfoBean
import com.kang.resume.bean.ResumeInfoBean
import com.kang.resume.custom.other.RedBookPresenter
import com.kang.resume.databinding.ResumeBaseInfoActivityBinding
import com.kang.resume.pro.IClick
import com.kang.resume.router.RouterConfig
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.bean.selectconfig.CropConfig
import com.ypx.imagepicker.data.OnImagePickCompleteListener
import java.util.*


/**
 * 类描述：
 * author:kanghuicong
 */
@Route(path = RouterConfig.BaseInfoRouter)
class BaseInfoActivity : BaseActivity<ResumeBaseInfoActivityBinding, BaseInfoModel>() {

    override fun ResumeBaseInfoActivityBinding.initBinding() {
        //手机号设置长度
        mBinding.inputPhone.setLength(11)
        //身高设置长度
        mBinding.inputHeight.setLength(3)
        //体重设置长度
        mBinding.inputWeight.setLength(3)

        //选择头像
        mBinding.clAvatar.setOnClickListener {
            ImagePicker.withMulti(RedBookPresenter())
                .mimeTypes(MimeType.ofImage())
                .filterMimeTypes(MimeType.GIF) //剪裁完成的图片是否保存在DCIM目录下
                //true：存储在DCIM下 false：存储在 data/包名/files/imagePicker/ 目录下
                .cropSaveInDCIM(false) //设置剪裁比例
                .setCropRatio(1, 1) //设置剪裁框间距，单位px
                .cropRectMinMargin(50) //是否圆形剪裁，圆形剪裁时，setCropRatio无效
                .cropAsCircle() //设置剪裁模式，留白或充满  CropConfig.STYLE_GAP 或 CropConfig.STYLE_FILL
                .cropStyle(CropConfig.STYLE_FILL) //设置留白模式下生成的图片背景色，支持透明背景
                .cropGapBackgroundColor(Color.TRANSPARENT)
                .crop(activity, OnImagePickCompleteListener {
                    //图片剪裁回调，主线程
                })

        }

        //选择性别
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
        //选择生日
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
        //选择参加工作时间
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
        //选择婚姻状况
        mBinding.inputMarryStatus.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.marry_status),
                        arrayOf(
                            getString(R.string.marry_status_unmarried),
                            getString(R.string.marry_status_married),
                            getString(R.string.marry_status_secret)
                        ),
                        null,
                        mVm.marryIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.marryIndex.value = position
                            mVm.baseInfoBean.marryStatus = text
                            mBinding.inputMarryStatus.setInput(text)
                        }
                    }.show()
            }

        })
        //选择政治面貌
        mBinding.inputPoliticalStatus.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.political_status),
                        arrayOf(
                            getString(R.string.political_status_cpc_member),
                            getString(R.string.political_status_probationary_member),
                            getString(R.string.political_status_youth_member),
                            getString(R.string.political_status_masses),
                            getString(R.string.political_status_democratic_parties),
                            getString(R.string.political_status_others)
                        ),
                        null,
                        mVm.politicalIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.politicalIndex.value = position
                            mVm.baseInfoBean.politicalStatus = text
                            mBinding.inputPoliticalStatus.setInput(text)
                        }
                    }.show()
            }

        })
        //保存
        mBinding.titleView.iClick = (object : IClick {
            override fun click(view: View) {

                val baseInfoBean = BaseInfoBean(
                    mVm.baseInfoBean.id, mVm.baseInfoBean.resumeId,
                    address = mBinding.inputAddress.getText(),
                    avatar = mVm.baseInfoBean.avatar,
                    birthday = mBinding.inputBirthday.getText(),
                    email = mBinding.inputEmail.getText(),
                    gender = mBinding.inputGender.getText(),
                    height = mBinding.inputHeight.getText(),
                    weight = mBinding.inputWeight.getText(),
                    marryStatus = mBinding.inputMarryStatus.getText(),
                    name = mBinding.inputName.getText(),
                    nation = mBinding.inputNation.getText(),
                    phone = mBinding.inputPhone.getText(),
                    politicalStatus = mBinding.inputPoliticalStatus.getText(),
                    province = mBinding.inputProvince.getText(),
                    startWorkTime = mBinding.inputStartWorkTime.getText()
                )

                mVm.keep(baseInfoBean)
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
            mBinding.inputAddress.setInput(baseInfo.address)
            mBinding.inputMarryStatus.setInput(baseInfo.marryStatus)
            mBinding.inputPoliticalStatus.setInput(baseInfo.politicalStatus)
            mBinding.inputNation.setInput(baseInfo.nation)
            mBinding.inputProvince.setInput(baseInfo.province)
            mBinding.inputHeight.setInput(baseInfo.height.toString())
            mBinding.inputWeight.setInput(baseInfo.weight.toString())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1000 -> {

                }
            }
        }
    }


}

