package com.kang.resume.preview.preview.XCRTemplate_pdf_1;

import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.FontsUtils;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_1 extends XCRTemplate_pdf_base  {
    private TextView hintResume;
    private TextView lineHint;
    private TextView hintTip;
    private XCRTemplate_pdf_1_View_1 lineTop;
    private TextView line;

    public XCRTemplate_pdf_1(Context context) {
        super(context);

        hintResume = findViewById(R.id.hint_resume);
        lineHint = findViewById(R.id.line_hint);
        hintTip = findViewById(R.id.hint_tip);
        lineTop = findViewById(R.id.line_top);
        line = findViewById(R.id.line);

    }

    @Override
    public int initLayout() {
        return R.layout.pager_1;
    }

    @Override
    public int _getLine() {
        return line.getMeasuredWidth();
    }

    @Override
    public void _setColor(int color) {
        lineHint.setBackgroundColor(color);
        lineTop.setColor(color);
        line.setBackgroundColor(color);
        hintResume.setTextColor(color);
        hintTip.setTextColor(color);
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {
        hintResume.setTextSize(TypedValue.COMPLEX_UNIT_PX, Config.textSize * 2f);
        hintTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, Config.textSize);

        lineHint.setBackgroundColor(Config.defaultColor);
        line.setBackgroundColor(Config.defaultColor);
        lineTop.setColor(Config.defaultColor);

        FontsUtils.setRegularFont(hintResume);
        FontsUtils.setRegularFont(hintTip);

        ConstraintLayout.LayoutParams lp0 = (ConstraintLayout.LayoutParams) hintResume.getLayoutParams();
        lp0.setMargins(Config.A4Padding, 0, 0, 0);
        hintResume.setLayoutParams(lp0);

        ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) lineHint.getLayoutParams();
        lp1.leftMargin = Config.textSize;
        lineHint.setLayoutParams(lp1);

        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) hintTip.getLayoutParams();
        lp2.leftMargin = Config.textSize / 2;
        hintTip.setLayoutParams(lp2);

        ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams) lineTop.getLayoutParams();
        lp3.height = Config.textSize;
        lineTop.setLayoutParams(lp3);

        FrameLayout.LayoutParams lp4 = (FrameLayout.LayoutParams) line.getLayoutParams();
        lp4.leftMargin = Config.A4Padding;
        line.setLayoutParams(lp4);

        FrameLayout.LayoutParams lp5 = (FrameLayout.LayoutParams) llItems.getLayoutParams();
        lp5.leftMargin = Config.A4Padding-Config.textSize;
        lp5.rightMargin = Config.A4Padding;
        lp5.topMargin = Config.textSize*2;
        llItems.setLayoutParams(lp5);
    }
}
