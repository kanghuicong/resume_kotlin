package com.kang.resume.preview.preview.XCRTemplate_pdf_6;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kang.resume.R;
import com.kang.resume.preview.custom.RadiusCardView;
import com.kang.resume.preview.preview.BaseTitleView;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.TitleUtils;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_6_TitleView extends BaseTitleView {

    private RadiusCardView cardView;
    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView line;
    private Context context;

    public XCRTemplate_pdf_6_TitleView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.pager_6_title, this, true);

        cardView = findViewById(R.id.card);
        ivIcon = findViewById(R.id.iv_icon);
        tvTitle = findViewById(R.id.tv_title);
        line = findViewById(R.id.line);

    }

    @Override
    public void _setTitleStr(String content) {
        tvTitle.setText(content);
        ivIcon.setImageDrawable(ContextCompat.getDrawable(context, TitleUtils.getIcon(content)));
    }

    @Override
    public void _setTitleSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size*1.2f);
        tvTitle.setPadding(size / 2, 0, size / 2, 0);

        TitleUtils.setIcon(cardView, ivIcon, size);

    }

    @Override
    public void _setColor(int color) {
        ivIcon.setBackgroundColor(color);
        line.setBackgroundColor(color);
    }


    @Override
    public int getRvRightMargin() {
        return Config.A4Padding;
    }


}
