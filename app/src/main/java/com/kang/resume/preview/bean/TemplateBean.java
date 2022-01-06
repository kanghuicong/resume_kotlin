package com.kang.resume.preview.bean;

import java.io.Serializable;

/**
 * 类描述：
 * author:kanghuicong
 */
public class TemplateBean implements Serializable {


    /**
     * className : XCRTemplate_pdf_0
     * xcrMainThemeStr : ff44546b
     * xcrSubThemeStr : ff44546b
     * xcrPreviewImgStr : template_thum_5_1
     * xcrName : 第六个简历
     * keys : 经典
     * tags :
     */

    private String className;
    private String xcrMainThemeStr;
    private String xcrSubThemeStr;
    private String xcrPreviewImgStr;
    private String xcrName;
    private String keys;
    private String tags;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getXcrMainThemeStr() {
        return xcrMainThemeStr;
    }

    public void setXcrMainThemeStr(String xcrMainThemeStr) {
        this.xcrMainThemeStr = xcrMainThemeStr;
    }

    public String getXcrSubThemeStr() {
        return xcrSubThemeStr;
    }

    public void setXcrSubThemeStr(String xcrSubThemeStr) {
        this.xcrSubThemeStr = xcrSubThemeStr;
    }

    public String getXcrPreviewImgStr() {
        return xcrPreviewImgStr;
    }

    public void setXcrPreviewImgStr(String xcrPreviewImgStr) {
        this.xcrPreviewImgStr = xcrPreviewImgStr;
    }

    public String getXcrName() {
        return xcrName;
    }

    public void setXcrName(String xcrName) {
        this.xcrName = xcrName;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
