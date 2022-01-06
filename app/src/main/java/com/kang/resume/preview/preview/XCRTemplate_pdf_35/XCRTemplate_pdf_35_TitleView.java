package com.kang.resume.preview.preview.XCRTemplate_pdf_35;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.kang.resume.R;
import com.kang.resume.preview.custom.RadiusCardView;
import com.kang.resume.preview.preview.BaseTitleView;
import com.kang.resume.preview.utils.Config;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_35_TitleView extends BaseTitleView {
    private RadiusCardView card;
    private TextView tvTitle;
    private TextView line;

    public XCRTemplate_pdf_35_TitleView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pager_35_title, this, true);

        card = findViewById(R.id.card);
        tvTitle = findViewById(R.id.tv_title);
        line = findViewById(R.id.line);

    }


    @Override
    public void _setTitleStr(String content) {
        tvTitle.setText(content);
    }

    @Override
    public void _setTitleSize(int size) {
        tvTitle.setPadding(Config.textSize * 2, 0, Config.textSize, 0);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 1.2f);

        LayoutParams lp4 = (LayoutParams) card.getLayoutParams();
        lp4.height = Config.textSize * 2;
        lp4.topMargin = Config.textSize / 5;
        lp4.bottomMargin = Config.textSize / 5;
        card.setLayoutParams(lp4);
        card.setRadius(Config.textSize * 2, Config.textSize * 2, 0, 0);
    }

    @Override
    public void _setColor(int color) {
        tvTitle.setBackgroundColor(color);
    }

    @Override
    public int getRvLeftMargin() {
        return Config.textSize * 2;
    }


    @Override
    public boolean isNameView() {
        return true;
    }

}
