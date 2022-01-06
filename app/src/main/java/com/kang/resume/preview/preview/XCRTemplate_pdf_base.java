package com.kang.resume.preview.preview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

//import com.jarvislau.destureviewbinder.GestureViewBinder;
import com.kang.resume.R;
import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.custom.ClipConstraintLayout;
import com.kang.resume.preview.pro.IView;
import com.kang.resume.preview.utils.Config;

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public abstract class XCRTemplate_pdf_base extends ConstraintLayout implements IView {
    public Context context;

    public LinearLayout llItems;
    public LinearLayout llPreview;
    public LinearLayout llView;
    public ClipConstraintLayout clPreviews;

    public abstract int initLayout();

    public abstract int _getLine();

    public abstract void _setColor(int color);

    public abstract void _setClipViewLayoutParams();

    public abstract void _setClickResumeBean(ResumeInfoBean bean);

    public XCRTemplate_pdf_base(Context context) {
        super(context);
        this.context = context;

        LayoutInflater.from(context).inflate(initLayout(), this, true);

        llItems = findViewById(R.id.ll_items);
        clPreviews = findViewById(R.id.cl_previews);
        llPreview = findViewById(R.id.ll_preview);

        //设置缩放
//        GestureViewBinder gestureViewBinder = GestureViewBinder.bind(context, llPreview, llPreview.getChildAt(0));
//        gestureViewBinder.setFullGroup(true);
    }

    @Override
    public LinearLayout getLinearLayout() {
        return llItems;
    }

    @Override
    public ClipConstraintLayout getClipView() {
        return clPreviews;
    }

    @Override
    public int getLineWidth() {
        return _getLine();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public IView setClipViewLayoutParams(int width) {
        //添加top/bottom间距
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) clPreviews.getLayoutParams();
        lp.width = width;
        lp.topMargin = Config.A4Padding;
        //不需要增加bottomMargin，完全依靠裁剪的高度更不容易出现问题
//        lp.bottomMargin = Config.A4Padding;
        clPreviews.setLayoutParams(lp);
        _setClipViewLayoutParams();
        return this;
    }

    @Override
    public void setColor(int color) {
        _setColor(color);
    }

    public void setClickResumeBean(ResumeInfoBean bean){
        _setClickResumeBean(bean);
    }

    @Override
    public void setData(List<View> views) {
        llItems.removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            llItems.addView(views.get(i));
        }

        TextView view = new TextView(context);

        llItems.addView(view);
    }

    @Override
    public int getPreviewWidth() {
        return clPreviews.getMeasuredWidth();
    }

    @Override
    public boolean needPosition() {
        return true;
    }

    @Override
    public boolean needTopMargin() {
        return true;
    }
}
