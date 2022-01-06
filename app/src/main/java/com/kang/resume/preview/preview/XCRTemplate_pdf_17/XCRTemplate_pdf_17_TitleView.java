package com.kang.resume.preview.preview.XCRTemplate_pdf_17;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.kang.resume.R;
import com.kang.resume.preview.preview.BaseTitleView;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_17_TitleView extends BaseTitleView {

    private TextView line;
    private TextView tvTitle;

    public XCRTemplate_pdf_17_TitleView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pager_16_title, this, true);

        line = findViewById(R.id.line);
        tvTitle = findViewById(R.id.tv_title);

        //借用pager_16_title布局，隐藏line
        line.setVisibility(GONE);
    }

    @Override
    public void _setTitleStr(String content) {
        tvTitle.setText(content);
    }

    @Override
    public void _setTitleSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size * 1.2f);
    }

    @Override
    public void _setColor(int color) {
        //nothing
    }

    @Override
    public int getSkillType() {
        return 1;
    }
}
