package com.kang.resume.preview.pro;

import android.view.View;
import android.widget.LinearLayout;

import com.kang.resume.bean.ResumeInfoBean;
import com.kang.resume.preview.custom.ClipConstraintLayout;

import java.util.List;

/**
 * 类描述：
 * author:kanghuicong
 */
public interface IView {

    LinearLayout getLinearLayout();

    ClipConstraintLayout getClipView();


    int getPreviewWidth();

    int getLineWidth();

    boolean needTopMargin();

    View getView();

    IView setClipViewLayoutParams(int width);

    void setColor(int color);

    void setClickResumeBean(ResumeInfoBean bean);

    void setData(List<View> views);

    boolean needPosition();
}
