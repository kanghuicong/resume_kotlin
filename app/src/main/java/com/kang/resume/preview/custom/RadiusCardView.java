package com.kang.resume.preview.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import androidx.cardview.widget.CardView;

import com.kang.resume.R;


public class RadiusCardView extends CardView {

    private float tlRadiu;
    private float trRadiu;
    private float brRadiu;
    private float blRadiu;
    Path path;

    public RadiusCardView(Context context) {
        this(context, null);
    }

    public RadiusCardView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialCardViewStyle);
    }

    public RadiusCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRadius(0);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RadiusCardView);
        tlRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_topLeftRadiu, 0);
        trRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_topRightRadiu, 0);
        brRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_bottomRightRadiu, 0);
        blRadiu = array.getDimension(R.styleable.RadiusCardView_rcv_bottomLeftRadiu, 0);
        setBackground(new ColorDrawable());

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        RectF rectF = getRectF();

        float[] readius = {tlRadiu,tlRadiu,trRadiu,trRadiu,brRadiu,brRadiu,blRadiu,blRadiu};
        path.addRoundRect(rectF,readius,Path.Direction.CW);
        canvas.clipPath(path,Region.Op.INTERSECT);
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

    }

    private RectF getRectF() {
        Rect rect = new Rect();
        getDrawingRect(rect);
        RectF rectF = new RectF(rect);
        return rectF;
    }

    public void setRadius(float tlRadiu,float blRadiu,float trRadiu,float brRadiu){
        this.tlRadiu = tlRadiu;
        this.blRadiu = blRadiu;
        this.trRadiu = trRadiu;
        this.brRadiu = brRadiu;
        invalidate();
    }

    public void setAllRadius(float radius){
        this.tlRadiu = radius;
        this.blRadiu = radius;
        this.trRadiu = radius;
        this.brRadiu = radius;
        invalidate();
    }
}