package com.kang.resume.preview.pro;

/**
 * 类描述：
 * author:kanghuicong
 */
public interface ITitle {

    void setTitleStr(String content);

    void setTitleSize(int size);

    void setColor(int color);

    int getRvLeftMargin();

    int getRvRightMargin();

    boolean isNameView();

    int getTimeWidth();

    //0 ：时间  学校  职位
    //1 ：学校  时间
    int getType();

    //0 ： 圆圈
    //1 ： 线条
    int getSkillType();
}
