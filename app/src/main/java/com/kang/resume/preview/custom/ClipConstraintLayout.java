package com.kang.resume.preview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

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
    public ClipConstraintLayout setClipView(int x, int y, int mX, int mY, int maxN, IMove iMove) {
        isFirst = false;

        Bitmap bitmap = loadBitmapFromView(mX, mY);
        //bitmap==null说明其实是空白页
        if (bitmap == null) return null;
        moveY = 0;
        moveTopY = 0;
        //理论上头部是不需要计算的，但实际却出现了误差
        //为了保证计算绝对无误，将头部误差加入计算
        int nY = getTopMoveX(bitmap, mX, y, maxN);

        //需要计算尾部是否分割了内容
        if (mY <= getMeasuredHeight()) {
            getBottomMoveX(bitmap, mX, mY, maxN);
        }


        this.x = x;
        this.y = y - nY;
        this.mX = mX;
        this.mY = mY - moveY;


        //返回最终上移的y
        iMove.moveY(moveY);
        invalidate();
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

    public Bitmap loadBitmapFromView(int mX, int mY) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//        RxLogTool.e("loadBitmapFromView---width:" + width + "----height:" + height + "----mX:" + mX + "----mY:" + mY);
        //因为切割，分页等各种原因，可能会出现多出一空白页的误差，需要特出处理
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        draw(c);
        return b;
    }

    public interface IMove {
        void moveY(int y);
    }
}
