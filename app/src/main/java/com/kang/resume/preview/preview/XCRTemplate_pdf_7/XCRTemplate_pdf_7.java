package com.kang.resume.preview.preview.XCRTemplate_pdf_7;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.TitleUtils;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_7 extends XCRTemplate_pdf_base {
    private TextView line;

    public XCRTemplate_pdf_7(Context context) {
        super(context);

        line = findViewById(R.id.line);
    }

    @Override
    public int initLayout() {
        return R.layout.pager_7;
    }

    @Override
    public int _getLine() {
        return line.getMeasuredWidth();
    }

    @Override
    public void _setColor(int color) {
        line.setBackgroundColor(color);
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {
        FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) llItems.getLayoutParams();
        lp1.leftMargin = Config.textSize * 2;
        lp1.rightMargin = Config.textSize * 2;
        llItems.setLayoutParams(lp1);


        FrameLayout.LayoutParams lp3 = (FrameLayout.LayoutParams) line.getLayoutParams();
        lp3.width = Config.textSize / 5;
        lp3.leftMargin = Config.textSize * 10 + (int) ((Config.textSize * TitleUtils.iconMultiple)/2);
        line.setLayoutParams(lp3);

    }

}
