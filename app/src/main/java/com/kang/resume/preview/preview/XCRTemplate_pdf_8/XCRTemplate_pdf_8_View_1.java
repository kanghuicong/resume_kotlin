package com.kang.resume.preview.preview.XCRTemplate_pdf_8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_8_View_1 extends ConstraintLayout {
    private Paint mPaint = new Paint();
    private Path path1 = new Path();

    public XCRTemplate_pdf_8_View_1(Context context) {
        super(context);
        init();
    }

    public XCRTemplate_pdf_8_View_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XCRTemplate_pdf_8_View_1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1f);
    }

    public float height = 0;
    public float width = 0;
    public float mHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = getMeasuredHeight();
        width = getMeasuredWidth();
        mHeight = getMeasuredWidth() - getMeasuredWidth() / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path1.moveTo(0, 0);
        path1.lineTo(width, 0);
        path1.lineTo(width, height);
        path1.lineTo(width / 2, mHeight);
        path1.lineTo(0, height);

        canvas.drawPath(path1, mPaint);
    }

}
