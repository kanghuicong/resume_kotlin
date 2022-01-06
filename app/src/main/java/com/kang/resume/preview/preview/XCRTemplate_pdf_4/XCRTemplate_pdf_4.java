package com.kang.resume.preview.preview.XCRTemplate_pdf_4;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
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
public class XCRTemplate_pdf_4 extends XCRTemplate_pdf_base {
    private LinearLayout llContent;
    private TextView hintResume;
    private TextView line;
    private XCRTemplate_pdf_4_View_1 lineTop;

    public XCRTemplate_pdf_4(Context context) {
        super(context);
        line = findViewById(R.id.line);
        llContent = findViewById(R.id.ll_content);
        hintResume = findViewById(R.id.hint_resume);
        lineTop = findViewById(R.id.line_top);

        FontsUtils.setRegularFont(hintResume);
        hintResume.setTextColor(Config.bigColor);
    }

    @Override
    public int initLayout() {
        return R.layout.pager_4;
    }

    @Override
    public int _getLine() {
        return line.getMeasuredWidth();
    }

    @Override
    public void _setColor(int color) {
        lineTop.setColor(color);
        hintResume.setTextColor(color);
        line.setBackgroundColor(color);
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {

        //标题边距
        ConstraintLayout.LayoutParams lp0 = (ConstraintLayout.LayoutParams) hintResume.getLayoutParams();
        lp0.setMargins(Config.A4Padding, 0, Config.A4Padding, 0);
        hintResume.setLayoutParams(lp0);


        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) llContent.getLayoutParams();
        lp.topMargin = Config.textSize/2;
        llContent.setLayoutParams(lp);
        llContent.setPadding(Config.A4Padding, 0, Config.A4Padding, 0);

        //标题大小
        hintResume.setTextSize(TypedValue.COMPLEX_UNIT_PX, Config.textSize * 2f);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) llItems.getLayoutParams();
        lp1.topMargin = Config.A4Padding / 4;
        llItems.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) line.getLayoutParams();
        lp3.width = Config.textSize / 5;
        line.setLayoutParams(lp3);
        line.setBackgroundColor(Config.defaultColor);


        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) lineTop.getLayoutParams();
        lp2.topMargin = Config.textSize / 3;
        lp2.height = Config.textSize / 2;
        lp2.width = clPreviews.getLayoutParams().width;
        lineTop.setLayoutParams(lp2);
        lineTop.setSize(Config.textSize, Config.A4Padding + line.getLayoutParams().width / 2);

    }

    @Override
    public boolean needPosition() {
        return false;
    }
}
