package com.kang.resume.preview.preview.XCRTemplate_pdf_14;

import android.content.Context;
import android.widget.LinearLayout;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.utils.Config;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_14 extends XCRTemplate_pdf_base {

    public XCRTemplate_pdf_14(Context context) {
        super(context);

    }

    @Override
    public int initLayout() {
        return R.layout.pager_16;
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
        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) llItems.getLayoutParams();
        lp5.leftMargin = Config.A4Padding;
        lp5.rightMargin = Config.A4Padding;
        llItems.setLayoutParams(lp5);
    }

    @Override
    public boolean needPosition() {
        return false;
    }

}
