package com.kang.resume.base;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

/**
 * 类描述：
 * author:kanghuicong
 */
public class ImageBindingAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("android:background")
    public static void setBackground(ImageView view, int resId) {
        view.setImageResource(resId);
    }
}

