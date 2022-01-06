package com.kang.resume.preview.preview.XCRTemplate_pdf_8;

import android.content.Context;
import android.util.TypedValue;
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
public class XCRTemplate_pdf_8 extends XCRTemplate_pdf_base {
    private TextView hintResume;
    private XCRTemplate_pdf_8_View_1 view1;

    public XCRTemplate_pdf_8(Context context) {
        super(context);
        hintResume = findViewById(R.id.hint_resume);
        view1 = findViewById(R.id.view1);
    }

    @Override
    public int initLayout() {
        return R.layout.pager_8;
    }

    @Override
    public int _getLine() {
        return 0;
    }

    @Override
    public void _setColor(int color) {
        hintResume.setBackgroundColor(color);
        hintResume.setTextColor(ColorUtils.reverseColor(color));
    }
    @Override
    public void _setClickResumeBean(ResumeInfoBean bean) {
    }

    @Override
    public void _setClipViewLayoutParams() {
        ConstraintLayout.LayoutParams lp5 = (ConstraintLayout.LayoutParams) llItems.getLayoutParams();
        lp5.leftMargin = Config.A4Padding;
        lp5.rightMargin = Config.A4Padding;
        lp5.topMargin = Config.textSize * 2;
        llItems.setLayoutParams(lp5);

        hintResume.setTextSize(TypedValue.COMPLEX_UNIT_PX, Config.textSize * 3);
        hintResume.setPadding(0, 0, Config.textSize, 0);
        hintResume.setBackgroundColor(Config.defaultColor);
        hintResume.setTextColor(ColorUtils.reverseColor(Config.defaultColor));

        hintResume.post(new Runnable() {
            @Override
            public void run() {
                int h = hintResume.getMeasuredHeight();
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) view1.getLayoutParams();
                lp.leftMargin = Config.A4Padding;
                lp.width = h + h / 4;
                lp.height = h + h / 4;
                view1.setLayoutParams(lp);
            }
        });

    }


    @Override
    public boolean needTopMargin() {
        return false;
    }
}
