package com.kang.resume.preview.pro;

import com.kang.resume.bean.ResumeInfoBean;

/**
 * 类描述：
 * author:kanghuicong
 */
public interface IInfo {
    void setColor(int color);

    void setData(ResumeInfoBean resumeBean, int size);

    //是否需要显示兴趣
    boolean hasInterest();

    //头像位置
    int avatarGravity();

    //info 数据内容是否居中
    boolean isCenterInfo();
}
