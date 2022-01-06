package com.kang.resume.preview.preview.XCRTemplate_pdf_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.preview.utils.ColorUtils;

/**
 * 类描述：
 * author:kanghuicong
 */
public class XCRTemplate_pdf_4_View_1 extends ConstraintLayout {

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private Path path1 = new Path();
    private Path path2 = new Path();
    private Path path3 = new Path();

    public XCRTemplate_pdf_4_View_1(Context context) {
        super(context);
        init();
    }

    public XCRTemplate_pdf_4_View_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XCRTemplate_pdf_4_View_1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        mPaint.setColor(ColorUtils.defaultColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1f);

        mPaint2.setColor(ColorUtils.defaultReverseColor);
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint2.setStrokeWidth(1f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxWidth = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    float firstWidth = 20;
    float height = 10;
    float spacing = 5;
    float maxWidth ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path1.moveTo(0, 0);
        path1.lineTo(firstWidth - height / 2 - spacing, 0);
        path1.lineTo(firstWidth - height / 2 - spacing, height);
        path1.lineTo(0, height);

        canvas.drawPath(path1, mPaint);
        canvas.drawCircle(firstWidth, height / 2, height / 2, mPaint);

        path2.moveTo(firstWidth + height / 2 + spacing, 0);
        path2.lineTo(firstWidth + height / 2 + spacing + 3 * firstWidth, 0);
        path2.lineTo(firstWidth + height / 2 + spacing + 3 * firstWidth, height);
        path2.lineTo(firstWidth + height / 2 + spacing, height);
        canvas.drawPath(path2, mPaint);

        path3.moveTo(firstWidth + height / 2 + spacing + 3 * firstWidth + spacing, 0);
        path3.lineTo(maxWidth, 0);
        path3.lineTo(maxWidth, height);
        path3.lineTo(firstWidth + height / 2 + spacing + 3 * firstWidth + spacing, height);
        canvas.drawPath(path3, mPaint2);


    }

    public void setSize(int size, int padding) {
        firstWidth = padding;
        spacing = (float) size /2;
        invalidate();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        //反转颜色
        mPaint2.setColor(ColorUtils.reverseColor(color));
        invalidate();
    }
}
