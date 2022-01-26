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
import com.kang.resume.http.ApiResponse
import com.kang.resume.http.HttpRequest
import com.kang.resume.pro.IClick
import com.kang.resume.resume.base.IDelete
import com.kang.resume.resume.base.IKeep
import com.kang.resume.router.RouterConfig
import com.kang.resume.utils.VerifyUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.vondear.rxtool.view.RxToast
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
        //简历名字设置长度
        mBinding.inputResumeName.setLength(15)
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
                                    val time = mVm.switchDate(date)
                                    mBinding.inputBirthday.setInput(time)

                                    mVm.switchTime(
                                        mVm.birthdayDefaultDate,
                                        time
                                    )
                                }
                            })
                    ).show()
            }
        })
        //选择学历
        mBinding.inputRecord.iClick = (object : IClick {
            override fun click(view: View) {
                XPopup.Builder(activity)
                    .isDestroyOnDismiss(true)
                    .asBottomList(
                        getString(R.string.record),
                        arrayOf(
                            getString(R.string.junior_high_school),
                            getString(R.string.high_school),
                            getString(R.string.technical_secondary_school),
                            getString(R.string.junior_college),
                            getString(R.string.undergraduate),
                            getString(R.string.master),
                            getString(R.string.doctor),
                            getString(R.string.postdoc),
                            getString(R.string.other),
                        ),
                        null,
                        mVm.recordIndex.value!!
                    ) { position,
                        text ->
                        run {
                            mVm.recordIndex.value = position
                            mBinding.inputRecord.setInput(text)
                        }
                    }.show()
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
                                    val time = mVm.switchDate(date)
                                    mBinding.inputStartWorkTime.setInput(time)

                                    mVm.switchTime(
                                        mVm.startWorkTimeDefaultDate,
                                        time
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
                            mBinding.inputPoliticalStatus.setInput(text)
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
                    startWorkTime = mBinding.inputStartWorkTime.getText(),
                    record = mBinding.inputRecord.getText(),
                    resumeName = mBinding.inputResumeName.getText()
                )

                mVm.keep(object : IKeep {
                    override suspend fun keep(): ApiResponse<Any> {
                        return HttpRequest.api().saveOrUpdateBaseInfo(baseInfoBean)
                    }
                })
            }
        })

        //删除
        mBinding.btDelete.setOnClickListener {
            mVm.delete(activity, object : IDelete {
                override suspend fun delete(): ApiResponse<Any> {
                    return HttpRequest.api().delBaseInfo(mVm.baseInfoBean.resumeId!!)
                }
            })
        }
    }

    override fun initLayout(): Int {
        return R.layout.resume_base_info_activity
    }

    override fun isInput(): Boolean {
        return !(mVm.baseInfoBean.address == mBinding.inputAddress.getText() &&
                mVm.baseInfoBean.birthday == mBinding.inputBirthday.getText() &&
                mVm.baseInfoBean.email == mBinding.inputEmail.getText() &&
                mVm.baseInfoBean.gender == mBinding.inputGender.getText() &&
                mVm.baseInfoBean.height == mBinding.inputHeight.getText() &&
                mVm.baseInfoBean.weight == mBinding.inputWeight.getText() &&
                mVm.baseInfoBean.marryStatus == mBinding.inputMarryStatus.getText() &&
                mVm.baseInfoBean.name == mBinding.inputName.getText() &&
                mVm.baseInfoBean.nation == mBinding.inputNation.getText() &&
                mVm.baseInfoBean.phone == mBinding.inputPhone.getText() &&
                mVm.baseInfoBean.politicalStatus == mBinding.inputPoliticalStatus.getText() &&
                mVm.baseInfoBean.province == mBinding.inputProvince.getText() &&
                mVm.baseInfoBean.startWorkTime == mBinding.inputStartWorkTime.getText() &&
                mVm.baseInfoBean.record == mBinding.inputRecord.getText() &&
                mVm.baseInfoBean.resumeName == mBinding.inputResumeName.getText())
    }

    override fun initViewModel(): BaseInfoModel {
        val resumeInfoBean: ResumeInfoBean? = getData()
        val baseInfoBean: BaseInfoBean? = getOtherData()

        initData(baseInfoBean)

        mBinding.vm =
            ViewModelProviderFactory.getViewModel(
                activity,
                BaseInfoModel(resumeInfoBean, baseInfoBean)
            )
        return mBinding.vm!!
    }

    //填入数据
    private fun initData(baseInfo: BaseInfoBean?) {

        if (baseInfo != null) {
            mBinding.inputResumeName.setInput(baseInfo.resumeName)
            mBinding.inputName.setInput(baseInfo.name)
            mBinding.inputEmail.setInput(baseInfo.email)
            mBinding.inputStartWorkTime.setInput(baseInfo.startWorkTime)
            mBinding.inputBirthday.setInput(baseInfo.birthday)
            mBinding.inputPhone.setInput(baseInfo.phone)
            mBinding.inputGender.setInput(baseInfo.gender)
            mBinding.inputRecord.setInput(baseInfo.record)
            mBinding.inputAddress.setInput(baseInfo.address)
            mBinding.inputMarryStatus.setInput(baseInfo.marryStatus)
            mBinding.inputPoliticalStatus.setInput(baseInfo.politicalStatus)
            mBinding.inputNation.setInput(baseInfo.nation)
            mBinding.inputProvince.setInput(baseInfo.province)
            mBinding.inputHeight.setInput(baseInfo.height)
            mBinding.inputWeight.setInput(baseInfo.weight)
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

