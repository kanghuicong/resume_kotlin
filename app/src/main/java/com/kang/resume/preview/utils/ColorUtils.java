package com.kang.resume.preview.utils;

import android.graphics.Color;

import java.util.regex.Pattern;

/**
 * 类描述：反转颜色
 * author:kanghuicong
 */
public class ColorUtils {

    public static final int defaultColor =  -12968418;
    public static final int defaultReverseColor =  -4408132;

    public static int reverseColor(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        //反转颜色
        int reverseColor = Color.rgb(Math.abs(red - 255), Math.abs(green - 255), Math.abs(blue - 255));
        if (reverseColor == -1) {
            reverseColor = -4408132;
        }

        return reverseColor;
    }

    public static int hexToColor(String color) {
        // #ff00CCFF
        color = "#"+color;
        String reg = "#[a-f0-9A-F]{8}";
        if (color == null || !Pattern.matches(reg, color)) {
            color = "#FF44546b";
        }
        return Color.parseColor(color);
    }

}
