package com.kang.resume.preview.preview.XCRTemplate_pdf_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kang.resume.preview.preview.BaseTitleCustomView;
import com.kang.resume.preview.utils.ColorUtils;
import com.kang.resume.preview.utils.Config;
import com.kang.resume.preview.utils.FontsUtils;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_4_TitleView extends BaseTitleCustomView {
    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private TextPaint txtPaint = new TextPaint();
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();
    private Path path4 = new Path();
    private String content;

    public XCRTemplate_pdf_4_TitleView(Context context) {
        super(context);

        mPaint.setColor(ColorUtils.defaultColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1f);

        mPaint2.setColor(ColorUtils.defaultReverseColor);
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint2.setStrokeWidth(1f);

        txtPaint.setColor(0xFFFFFFFF);
        txtPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        txtPaint.setTextSize(28);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        FontsUtils.setRegularFont(txtPaint);
        content = "";

    }

    float height;
    float maxWidth;
    float space = 10f;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //画笔宽度为2f *3
        maxWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path1.moveTo(0, 0);
        path1.lineTo(0, height);
        path1.lineTo(height * 3 + height / 2, height);
        path1.lineTo(height * 3, 0);

        path2.moveTo(height * 3 + space, 0);
        path2.lineTo(height * 3 + space + height / 4, 0);
        path2.lineTo(height * 3 + height / 2 + space + height / 4, height);
        path2.lineTo(height * 3 + height / 2 + space, height);

        path3.moveTo(height * 3 + space + height / 4 + space, 0);
        path3.lineTo(height * 3 + space + height / 4 + space + height / 2 + space, 0);
        path3.lineTo(height * 3 + space + height / 4 + space + height / 2 + space, height);
        path3.lineTo(height * 3 + space + height / 4 + space + height / 2, height);

        path4.moveTo(height * 3 + space + height / 4 + space + height / 2 + space, 0);
        path4.lineTo(height * 3 + space + height / 4 + space + height / 2 + space + height / 2, height);
        path4.lineTo(height * 3 + space + height / 4 + space + height / 2 + space, height);

        canvas.drawPath(path1, mPaint);
        canvas.drawPath(path2, mPaint);
        canvas.drawPath(path3, mPaint);
        canvas.drawPath(path4, mPaint2);
        canvas.drawLine(0, height, maxWidth, height, mPaint);

        float offset = Math.abs(txtPaint.ascent() + txtPaint.descent()) / 2;

        canvas.drawText(content, (float) (height * 3 / 2), height / 2 + offset, txtPaint);
    }

    @Override
    public void _setTitleStr(String content) {
        this.content = content;
        invalidate();
    }

    @Override
    public void _setTitleSize(int size) {
        //标题size 是内容size的两倍
        txtPaint.setTextSize(size);
        FontsUtils.setRegularFont(txtPaint);
        height = size * 2;
        space = height / 4;
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size * 2 + 6));

        invalidate();
    }

    @Override
    public void _setColor(int color) {
        mPaint.setColor(color);
        //反转颜色
        mPaint2.setColor(ColorUtils.reverseColor(color));

        invalidate();
    }

    @Override
    public int getRvLeftMargin() {
        return Config.textSize;
    }




}
