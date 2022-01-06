package com.kang.resume.preview.preview.XCRTemplate_pdf_0;

import android.content.Context;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_0 extends XCRTemplate_pdf_base {

    public XCRTemplate_pdf_0(Context context) {
        super(context);

    }

    @Override
    public int initLayout() {
        return R.layout.pager_0;
    }

    @Override
    public int _getLine() {
        return 0;
    }

    @Override
    public void _setColor(int color) {
        //nothing
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {
        //nothing
    }

}
