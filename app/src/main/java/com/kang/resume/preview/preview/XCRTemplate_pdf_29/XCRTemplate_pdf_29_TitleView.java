package com.kang.resume.preview.preview.XCRTemplate_pdf_29;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kang.resume.R;
import com.kang.resume.preview.preview.BaseTitleView;
import com.kang.resume.preview.utils.Config;
import com.vondear.rxtool.RxLogTool;

public class XCRTemplate_pdf_29_TitleView extends BaseTitleView {
    private LinearLayout bgTv;
    private TextView line;
    private TextView tvTitle;


    public XCRTemplate_pdf_29_TitleView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pager_29_title, this, true);
        bgTv = findViewById(R.id.bg_tv);
        line = findViewById(R.id.line);
        tvTitle = findViewById(R.id.tv_title);
    }


    @Override
    public void _setTitleStr(String content) {
        tvTitle.setText(content);
    }

    @Override
    public void _setTitleSize(int size) {
        RxLogTool.e("size::::::"+size);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)bgTv.getLayoutParams() ;
        lp.leftMargin = Config.A4Padding;
        bgTv.setLayoutParams(lp);


        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams)line.getLayoutParams() ;
        lp1.rightMargin = Config.A4Padding;
        line.setLayoutParams(lp1);

        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 1.2f);
        tvTitle.setPadding(size / 2, size / 4, size / 2, size / 4);
    }

    @Override
    public void _setColor(int color) {
        bgTv.setBackgroundColor(color);
        line.setBackgroundColor(color);
    }

    @Override
    public int getSkillType() {
        return 1;
    }

    @Override
    public int getRvLeftMargin() {
        return Config.A4Padding;
    }

    @Override
    public int getRvRightMargin() {
        return Config.A4Padding;
    }

}
