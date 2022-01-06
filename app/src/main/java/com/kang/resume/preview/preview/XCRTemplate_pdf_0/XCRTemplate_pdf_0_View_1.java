package com.kang.resume.preview.preview.XCRTemplate_pdf_0;

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
public class XCRTemplate_pdf_0_View_1 extends ConstraintLayout {

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private Path path1 = new Path();
    private Path path2 = new Path();
    private float maxWidth;

    public XCRTemplate_pdf_0_View_1(Context context) {
        super(context);
        init();
    }

    public XCRTemplate_pdf_0_View_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XCRTemplate_pdf_0_View_1(Context context, AttributeSet attrs, int defStyleAttr) {
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


    float height = 18;
    float divideHeight = 0;
    //间距
    float spacing = 5f;

    float xSpacing = 30f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        divideHeight = height / 3;

        path1.moveTo(0, 0);
        path1.lineTo(maxWidth / 2 - spacing, 0);
        path1.lineTo(maxWidth / 2 - spacing, divideHeight);
        path1.lineTo(maxWidth / 2 - spacing - xSpacing, divideHeight);
        path1.lineTo(maxWidth / 2 - spacing - xSpacing, divideHeight * 2);
        path1.lineTo(maxWidth / 2 - spacing, divideHeight * 2);
        path1.lineTo(maxWidth / 2 - spacing, divideHeight * 3);
        path1.lineTo(0, divideHeight * 3);
        path1.lineTo(0, divideHeight * 2);
        path1.lineTo(xSpacing, divideHeight * 2);
        path1.lineTo(xSpacing, divideHeight);
        path1.lineTo(0, divideHeight);

        path2.moveTo(maxWidth / 2 + spacing, 0);
        path2.lineTo(maxWidth, 0);
        path2.lineTo(maxWidth, divideHeight);
        path2.lineTo(maxWidth - xSpacing, divideHeight);
        path2.lineTo(maxWidth - xSpacing, divideHeight * 2);
        path2.lineTo(maxWidth, divideHeight * 2);
        path2.lineTo(maxWidth, divideHeight * 3);
        path2.lineTo(maxWidth / 2 + spacing, divideHeight * 3);
        path2.lineTo(maxWidth / 2 + spacing, divideHeight * 2);
        path2.lineTo(maxWidth / 2 + spacing + xSpacing, divideHeight * 2);
        path2.lineTo(maxWidth / 2 + spacing + xSpacing, divideHeight);
        path2.lineTo(maxWidth / 2 + spacing, divideHeight);

        canvas.drawPath(path1, mPaint);
        canvas.drawPath(path2, mPaint2);

    }

    public void setColor(int color) {
        mPaint.setColor(color);
        //反转颜色
        mPaint2.setColor(ColorUtils.reverseColor(color));
        invalidate();
    }
}
