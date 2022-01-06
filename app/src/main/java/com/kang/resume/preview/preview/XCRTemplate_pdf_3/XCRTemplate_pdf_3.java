package com.kang.resume.preview.preview.XCRTemplate_pdf_3;

import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.custom.RadiusCardView;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.utils.Config;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_3 extends XCRTemplate_pdf_base {
    private LinearLayout llBg;
    private RadiusCardView llHint;
    private TextView hintResume;
    private TextView hintTip;
    private TextView lineHint;
    private FrameLayout llContent;
    public TextView line;

    @Override
    public int initLayout() {
        return R.layout.pager_3;
    }

    @Override
    public int _getLine() {
        return line.getMeasuredWidth();
    }
    @Override
    public void _setColor(int color) {
        llBg.setBackgroundColor(color);
//        line.setBackgroundColor(color);
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {


        FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) llItems.getLayoutParams();
        lp1.topMargin = Config.A4Padding / 4;
        llItems.setLayoutParams(lp1);


        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) llContent.getLayoutParams();
        lp2.topMargin = Config.textSize / 3;
        llContent.setLayoutParams(lp2);
        llContent.setPadding(Config.A4Padding, 0, Config.A4Padding, 0);


        FrameLayout.LayoutParams lp3 = (FrameLayout.LayoutParams) line.getLayoutParams();
        lp3.width = Config.textSize / 5;
        lp3.leftMargin = Config.textSize;
        line.setLayoutParams(lp3);
        line.setBackgroundColor(Config.defaultColor);


        ConstraintLayout.LayoutParams lp4 = (ConstraintLayout.LayoutParams) llHint.getLayoutParams();
        lp4.height = Config.textSize * 5;
        llHint.setLayoutParams(lp4);
        llHint.setRadius(0, 0, Config.textSize * 3, Config.textSize * 3);

        //标题大小
        LinearLayout.LayoutParams lp6 = (LinearLayout.LayoutParams) hintResume.getLayoutParams();
        lp6.leftMargin = Config.textSize * 3;
        hintResume.setLayoutParams(lp6);

        hintResume.setTextSize(TypedValue.COMPLEX_UNIT_PX, Config.textSize * 2f);

        //中间白线
        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) lineHint.getLayoutParams();
        lp5.topMargin = Config.textSize / 2;
        lp5.bottomMargin = Config.textSize / 2;
        lp5.leftMargin = Config.textSize / 2;
        lp5.rightMargin = Config.textSize / 2;
        lineHint.setLayoutParams(lp5);

        //副标题
        LinearLayout.LayoutParams lp7 = (LinearLayout.LayoutParams) hintTip.getLayoutParams();
        lp7.rightMargin = Config.textSize * 3;

        hintTip.setLayoutParams(lp7);
        hintTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, Config.textSize);

//        line.setBackgroundColor(Config.defaultColor);
//        llBg.setBackgroundColor(Config.defaultColor);
    }

    public XCRTemplate_pdf_3(Context context) {
        super(context);

        llHint = findViewById(R.id.ll_hint);
        llBg = findViewById(R.id.ll_bg);
        hintResume = findViewById(R.id.hint_resume);
        hintTip = findViewById(R.id.hint_tip);
        llContent = findViewById(R.id.ll_content);
        line = findViewById(R.id.line);
        lineHint = findViewById(R.id.line_hint);
    }

}
