package com.kang.resume.preview.utils;

/**
 * 类描述：
 * author:kanghuicong
 */
public class Config {
    public static final String INFO = "个人信息";
    public static final String POSITION = "求职意向";
    public static final String EDUCATION = "教育背景";
    public static final String JOB = "工作经历";
    public static final String PROJECT = "项目经验";
    public static final String SKILLS = "专业技能";
    public static final String AWARD = "证书奖项";
    public static final String INTEREST = "兴趣爱好";
    public static final String ASSESSMENT = "自我评价";

    public static final float avatarRatio = 413f / 295f;

    // A4纸内边距
    public static int A4Padding = 60;

    //设置A4纸内边距
    public static void setA4Padding(int padding) {
        A4Padding = padding;
    }

    //文字大小
    public static int textSize = 0;

    public static void setTextSize(int size) {
        textSize = size;
    }

    //默认颜色
    public static int chooseColor = 0xFF000000;

    public static final int defaultColor = 0xFF44546b;

    public static void setChooseColor(int color) {
        chooseColor = color;
    }

    // 最大的标题，  如 ：姓名    xxx的个人简历 等
    public static int bigColor = 0xFF232323;
    //个人信息的字体   如：姓    名：李建鹏
    public static int infoColor = 0xFF333333;
    //每一个模块的标题字体等  如：工作经历 等
    public static int headColor = 0xFF000000;
    //企业、学校名称
    public static int titleHighColor = 0xFF333333;
    //软件开发 专业等。
    public static int titleLowColor = 0xFF666666;
    //具体内容
    public static int bodyColor = 0xFF333333;
    //名字字体，有的模板名字字体需要单独设置，暂时不提供改动
    public static int nameColor = 0xFF000000;


    public static int mWidth = 0;
    public static int mHeight = 0;
    public static int mX = 0;
}
