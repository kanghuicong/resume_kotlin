package com.kang.resume.base

import com.kang.resume.bean.TemplateCoverListBean

/**
 * 类描述：
 * author:kanghuicong
 */
object ValueConfig {

    const val BASE_URL = "http://82.157.168.250:8080"

    const val space = " - "

    const val classPageName = "com.kang.resume.preview.preview."

    //首页封面列表
    var templateList = mutableListOf<TemplateCoverListBean>()

    //熟练度对应的值
    val proficiencyValueList = intArrayOf(50,65,80,90)

    init {
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_0", "template_thum_5_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_1", "template_thum_6_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_3", "template_thum_7_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_4", "template_thum_0_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_6", "template_thum_4_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_7", "template_thum_10_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_8", "template_thum_24_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_14", "template_thum_9_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_16", "template_thum_13_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_17", "template_thum_14_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_21", "template_thum_20_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_29", "template_thum_29_1"))
        templateList.add(TemplateCoverListBean("XCRTemplate_pdf_35", "template_thum_35_1"))

    }

}