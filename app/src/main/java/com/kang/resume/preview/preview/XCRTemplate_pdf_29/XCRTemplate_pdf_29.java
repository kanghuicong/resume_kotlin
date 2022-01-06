package com.kang.resume.preview.preview.XCRTemplate_pdf_29;

import android.content.Context;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;

public class XCRTemplate_pdf_29 extends XCRTemplate_pdf_base{

    public XCRTemplate_pdf_29(Context context){
        super(context);

    }
    @Override
    public int initLayout() {
        return R.layout.pager_29;
    }

    @Override
    public int _getLine() {
        return 0;
    }

    @Override
    public void _setColor(int color) {

    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {

    }
    @Override
    public boolean needTopMargin() {
        return false;
    }
    @Override
    public void _setClipViewLayoutParams() {

    }
}
