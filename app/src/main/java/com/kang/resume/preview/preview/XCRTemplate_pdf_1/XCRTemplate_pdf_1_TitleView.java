package com.kang.resume.preview.preview.XCRTemplate_pdf_1;

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
public class XCRTemplate_pdf_1_TitleView extends BaseTitleCustomView {
    private Paint mPaint = new Paint();
    private TextPaint txtPaint = new TextPaint();
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();
    private String content;

    public XCRTemplate_pdf_1_TitleView(Context context) {
        super(context);

        mPaint.setColor(ColorUtils.defaultColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1f);

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

    float widthMul = 3.5f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path1.moveTo(0, 0);
        path1.lineTo(height * widthMul, 0);
        path1.lineTo(height * widthMul + height, height);
        path1.lineTo(Config.textSize, height);
        path1.lineTo(Config.textSize, height + Config.textSize / 2f);
        path1.lineTo(0, height);


        path2.moveTo(height * widthMul + space, 0);
        path2.lineTo(height * widthMul + space + height / 5, 0);
        path2.lineTo(height * widthMul + height + space + height / 5, height);
        path2.lineTo(height * widthMul + height + space, height);

        path3.moveTo(height * widthMul + space + height / 5 + space, 0);
        path3.lineTo(height * widthMul + space + height / 5 + space + height / 3, 0);
        path3.lineTo(height * widthMul + space + height / 5 + space + height + height / 3, height);
        path3.lineTo(height * widthMul + space + height / 5 + space + height, height);

        canvas.drawPath(path1, mPaint);
        canvas.drawPath(path2, mPaint);
        canvas.drawPath(path3, mPaint);
        canvas.drawLine(0, height, maxWidth, height, mPaint);

        float offset = Math.abs(txtPaint.ascent() + txtPaint.descent()) / 2;

        canvas.drawText(content, (float) (height * widthMul / 2)+ space, height / 2 + offset , txtPaint);
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
        invalidate();
    }

    @Override
    public int getRvLeftMargin() {
        return Config.textSize * 2;
    }

    @Override
    public int getRvRightMargin() {
        return Config.textSize * 2;
    }

    @Override
    public boolean isNameView() {
        return true;
    }

}
