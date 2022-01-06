package com.kang.resume.preview.preview.XCRTemplate_pdf_0;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kang.resume.R;
import com.kang.resume.preview.preview.BaseTitleView;
import com.kang.resume.preview.utils.Config;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_0_TitleView extends BaseTitleView {

    private LinearLayout layout;
    private TextView line1;
    private TextView line2;
    private TextView tvTitle;
    private TextView line3;

    public XCRTemplate_pdf_0_TitleView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pager_0_title, this, true);

        layout = findViewById(R.id.layout);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        tvTitle = findViewById(R.id.tv_title);
        line3 = findViewById(R.id.line3);

    }

    @Override
    public void _setTitleStr(String content) {
        tvTitle.setText(content);
    }

    @Override
    public void _setTitleSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size*1.2f);
        tvTitle.setPadding(Config.textSize, 0, Config.textSize * 2, 0);

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) layout.getLayoutParams();
        lp.height = size * 2;
        layout.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) line1.getLayoutParams();
        lp1.width = size / 2;
        lp1.rightMargin = size / 4;
        line1.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) line2.getLayoutParams();
        lp2.width = size / 4;
        lp2.rightMargin = size / 4;
        line2.setLayoutParams(lp2);

        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) line3.getLayoutParams();
        line3.setLayoutParams(lp3);
    }

    @Override
    public void _setColor(int color) {
        tvTitle.setBackgroundColor(color);
        line1.setBackgroundColor(color);
        line2.setBackgroundColor(color);
    }

    @Override
    public int getRvLeftMargin() {
        return Config.textSize*2;
    }

    @Override
    public int getRvRightMargin() {
        return Config.textSize*2;
    }

}
