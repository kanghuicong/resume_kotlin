package com.kang.resume.preview.preview.XCRTemplate_pdf_6;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.preview.XCRTemplate_pdf_base;
import com.kang.resume.preview.utils.Config;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_6 extends XCRTemplate_pdf_base {
    TextView line;

    public XCRTemplate_pdf_6(Context context) {
        super(context);
        line = findViewById(R.id.line);
    }

    @Override
    public int initLayout() {
        return R.layout.pager_6;
    }

    @Override
    public int _getLine() {
        return 0;
    }

    @Override
    public void _setColor(int color) {
        line.setBackgroundColor(color);
    }

    @Override
    public void _setClipViewLayoutParams() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line.getLayoutParams();
        lp.width = Config.textSize * 2;
        line.setLayoutParams(lp);

        ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) llItems.getLayoutParams();
        lp1.leftMargin = Config.textSize * 2;
        llItems.setLayoutParams(lp1);
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public int getPreviewWidth() {
        return clPreviews.getMeasuredWidth() + line.getMeasuredWidth();
    }

}
