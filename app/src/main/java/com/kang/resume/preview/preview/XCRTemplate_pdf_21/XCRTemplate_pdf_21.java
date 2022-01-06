package com.kang.resume.preview.preview.XCRTemplate_pdf_21;

import android.content.Context;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.utils.ColorUtils;
import com.kang.resume.preview.utils.Config;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_21 extends XCRTemplate_pdf_base {
    private TextView line1;
    private TextView line2;

    public XCRTemplate_pdf_21(Context context) {
        super(context);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
    }

    @Override
    public int initLayout() {
        return R.layout.pager_21;
    }

    @Override
    public int _getLine() {
        return 0;
    }

    @Override
    public void _setColor(int color) {
        line1.setBackgroundColor(color);
        line2.setBackgroundColor(ColorUtils.reverseColor(color));
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {
        LayoutParams lp5 = (LayoutParams) llItems.getLayoutParams();
        lp5.leftMargin = Config.A4Padding;
        lp5.rightMargin = Config.A4Padding;
        lp5.topMargin = Config.textSize * 2;
        llItems.setLayoutParams(lp5);

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) line1.getLayoutParams();
        lp.height = Config.textSize;
        lp.topMargin = Config.textSize/2;
        line1.setLayoutParams(lp);

        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) line2.getLayoutParams();
        lp2.height = Config.textSize / 4;
        line2.setLayoutParams(lp2);

        line1.setBackgroundColor(Config.defaultColor);
        line2.setBackgroundColor(ColorUtils.reverseColor(Config.defaultColor));
    }

    @Override
    public boolean needTopMargin() {
        return false;
    }
}
