package com.kang.resume.preview.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.TextView;


/**
 * 类描述：
 */
public class FontsUtils {

    private static AssetManager mgr;

    public static void init(Context context) {
        if (mgr == null) mgr = context.getAssets();
    }

    public static void setRegularFont(TextView tv) {
        return;
//        Typeface tf = Typeface.createFromAsset(mgr, "Montserrat-Regular.otf");
////        Typeface tf = Typeface.createFromAsset(mgr, "flutter_assets/fonts/NotoSansSC-Medium.otf");
//        tv.setTypeface(tf);
    }

    public static void setRegularFont(TextPaint paint){
//        paint.setTypeface(Typeface.createFromAsset(mgr, "Montserrat-Regular.otf"));
    }

}
