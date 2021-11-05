package com.kang.resume.bean

/**
 * 类描述：
 * author:kanghuicong
 */
class TemplateBean(className:String,cover:String) {
    var className: String? = null
    var cover: String? = null
    var resId: Int = 0

    init {
        this.className = className
        this.cover = cover
    }

}