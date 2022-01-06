package com.kang.resume.preview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 类描述：
 * author:kanghuicong
 */
public class ProgressView extends View {
    private Paint paint;
    private Paint txtPaint;
    private Paint xPaint;

    //默认宽度
    private int width = 100;
    //线宽
    private float strokeWidth = 8;
    //角度
    private float angle = 0;
    private String angleTxt = "0%";

    private RectF oval;
    private int color = Color.BLACK;
    private float offset;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);

        xPaint = new Paint();

        xPaint.setAntiAlias(true);
        xPaint.setColor(color);
        xPaint.setAlpha(100);
        xPaint.setStrokeWidth(strokeWidth);
        xPaint.setStyle(Paint.Style.STROKE);

        txtPaint = new Paint();
        txtPaint.setColor(color);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        txtPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        txtPaint.setTextSize(28);

        offset = Math.abs(txtPaint.ascent() + txtPaint.descent()) / 2;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();

        oval = new RectF(strokeWidth / 2, strokeWidth / 2,
                width - strokeWidth / 2, width - strokeWidth / 2);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(oval, -90, angle, false, paint);
        canvas.drawArc(oval, angle - 90, 360 - angle, false, xPaint);

        canvas.drawText(angleTxt, width / 2, width / 2 + offset, txtPaint);

    }

    public void setAngle(float angle) {
        angleTxt = (int) (angle * 100) + "%";

        if (angle < 1) {
            this.angle = angle * 360;
        } else this.angle = angle;

        invalidate();
    }

    public void setColor(int color) {
        paint.setColor(color);
        xPaint.setColor(color);
        xPaint.setAlpha(100);
        txtPaint.setColor(color);
        invalidate();
    }

    public void setSize(int size) {
        txtPaint.setTextSize(size);
        strokeWidth = (float) (size / 3);
        paint.setStrokeWidth(strokeWidth);
        xPaint.setStrokeWidth(strokeWidth);
        offset = Math.abs(txtPaint.ascent() + txtPaint.descent()) / 2;
        invalidate();
    }
}
