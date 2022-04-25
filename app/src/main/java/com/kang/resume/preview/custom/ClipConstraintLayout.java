package com.kang.resume.preview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.kang.resume.R;
import com.kang.resume.base.ValuableConfig;
import com.kang.resume.preview.pro.IView;
import com.kang.resume.preview.utils.Config;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.view.RxToast;

/**
 * 类描述：
 * author:kanghuicong
 */


public class ClipConstraintLayout extends ConstraintLayout {
    public ClipConstraintLayout(Context context) {
        this(context, null);
    }

    public ClipConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    boolean isFirst = true;
    int x;
    int y;
    int mX;
    int mY;

    public int reY;
    public int reX;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFirst) {
            x = 0;
            y = 0;
            mX = getMeasuredWidth();
            mY = getMeasuredHeight();
        }
        reY = getMeasuredHeight();
        reX = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipRect(x, y, mX, mY);

    }


    //maxN: 如果像素点低于maxN说明这一行是空白
    public ClipConstraintLayout setClipView(int x, int y, int mX, int mY, int maxN, int index, IMove iMove) {
        isFirst = false;

        if (mX != 0) Config.mX = mX;
        if (mX == 0 && Config.mX != 0) mX = Config.mX;

        RxLogTool.e("setClipView--x:" + x + "----y:" + y + "-----mX:" + mX + "-----mY:" + mY);

//        Bitmap bitmap = loadBitmapFromView();

        //bitmap==null说明其实是空白页
//        if (bitmap == null) return null;
        moveY = 0;
        moveTopY = 0;
        //理论上头部是不需要计算的，但实际却出现了误差
        //为了保证计算绝对无误，将头部误差加入计算
//        int nY = getTopMoveX(bitmap, mX, y, maxN);
        int nY = 0;
        //需要计算尾部是否分割了内容
//        getBottomMoveX(bitmap, Config.mWidth - 1, Config.mWidth - 1, maxN);


        this.x = x;
        this.y = y - nY;
        this.mX = mX;
        this.mY = mY - moveY;


        //返回最终上移的y
        iMove.moveY(moveY);
        invalidate();

//        Bitmap bitmap = loadBitmapFromView();
//        getBottomMoveX(bitmap, this.mX, this.mY, maxN);
//
//        this.x = x;
//        this.y = y - nY;
//        this.mX = mX;
//        this.mY = mY - moveY;
//        iMove.moveY(moveY);

        return this;
    }

    int moveY = 0;

    //判断该mY-1这一行的像素点
    private int getBottomMoveX(Bitmap bitmap, int mX, int mY, int maxN) {
        int n = 0;
        for (int i = 0; i < mX; i++) {
            if (mY - 1 <= 0) break;
            if (mY >= bitmap.getHeight() || mX > bitmap.getWidth()) break;
            int pixel = bitmap.getPixel(i, mY);
//            RxLogTool.e("mY:" + mY + "----mX:" + i +"-----pixel:" + pixel);
            if (pixel != -1) n++;
            if (n > maxN) {
                moveY++;
                getBottomMoveX(bitmap, mX, mY - 1, maxN);
                break;
            }
        }
        return moveY;
    }

    int moveTopY = 0;

    private int getTopMoveX(Bitmap bitmap, int mX, int mY, int maxN) {
        int n = 0;
        for (int i = 0; i < mX; i++) {
            if (mY - 1 <= 0) break;
            if (mY >= bitmap.getHeight() || mX > bitmap.getWidth()) break;
            int pixel = bitmap.getPixel(i, mY);
            if (pixel != -1) n++;
            if (n > maxN) {
                moveTopY++;
                getTopMoveX(bitmap, mX, mY - 1, maxN);
                break;
            }
        }
        return moveTopY;
    }


    public Bitmap loadBitmapFromView() {
//        measure(0, 0);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width != 0) Config.mWidth = width;
        if (height != 0) Config.mHeight = height;
        if (width == 0 && Config.mWidth != 0) width = Config.mWidth;
        if (height == 0 && Config.mHeight != 0) height = Config.mHeight;

        RxLogTool.e("setClipView---width:" + width + "----height:" + height);
        //因为切割，分页等各种原因，可能会出现多出一空白页的误差，需要特出处理
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap b = Bitmap.createBitmap(width, 200, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }

    public int getXWidth() {
//        measure(0, 0);
        return getMeasuredWidth();
    }

    public void moveBY(boolean isAdd) {
        if (isAdd) {
            this.mY = this.mY - 10;
        } else {
            this.mY = this.mY + 10;
        }
        invalidate();
    }

    public void moveTY(boolean isAdd) {
        LinearLayout.MarginLayoutParams lp = (LinearLayout.MarginLayoutParams) getLayoutParams();

        if (isAdd) {
            this.y = this.y - 10;
            lp.topMargin = lp.topMargin + 10;
        } else {
            this.y = this.y + 10;
            lp.topMargin = lp.topMargin - 10;
        }
        setLayoutParams(lp);

        invalidate();
    }

    public void  moveY(int y){
        this.y = this.y +y;

    }


    public interface IMove {
        void moveY(int y);
    }
}
