package com.kang.resume.preview.preview;

import android.content.Context;
import android.widget.LinearLayout;

import com.kang.resume.preview.pro.ITitle;

/**
 * 类描述：
 * author:kanghuicong
 */
public abstract class BaseTitleView extends LinearLayout implements ITitle {

    public abstract void _setTitleStr(String content);
    public abstract void _setTitleSize(int size);
    public abstract void _setColor(int color);

    public BaseTitleView(Context context) {
        super(context);
    }

    @Override
    public void setTitleStr(String content) {
        _setTitleStr(content);
    }

    @Override
    public void setTitleSize(int size) {
        _setTitleSize(size);
    }

    @Override
    public void setColor(int color) {
        _setColor(color);
    }

    @Override
    public int getRvLeftMargin() {
        return 0;
    }

    @Override
    public int getRvRightMargin() {
        return 0;
    }

    @Override
    public boolean isNameView() {
        return false;
    }

      @Override
    public int getTimeWidth(){
        return 0;
    }

    @Override
    public int getType(){
        return 0;
    }

    @Override
    public int getSkillType(){
        return 0;
    }

}
